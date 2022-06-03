<template>
  <div>
    <div style="margin-bottom: 10px">
      <h2 style="display: inline">题目信息</h2>
      <div style="float: right">
        <el-button
          @click="
            clearFormFields();
            this.status = '新增';
            dialogFormVisible = true;
          "
          >新增</el-button
        >
        <el-button type="danger" @click="del(this.multiSelection)"
          >删除</el-button
        >
      </div>
      <el-dialog
        :title="status + '题目信息'"
        v-model="dialogFormVisible"
        width="600px"
      >
        <el-form
          :model="questionForm"
          :rules="formRules"
          ref="questionForm"
          label-width="200px"
          label-position="right"
        >
          <el-form-item label="所属科目" prop="subjectId">
            <el-select
              filterable
              placeholder="请选择所属科目"
              @change="valueToSubjectId"
              v-model="questionForm.subjectId"
            >
              <el-option
                v-for="subject in subjectList"
                :key="subject.subjectId"
                :label="subject.subjectName"
                :value="subject.subjectId"
              >
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="题目类型" prop="typeId">
            <el-select
              filterable
              placeholder="请选择题目类型"
              @change="valueToTypeId"
              v-model="questionForm.typeId"
            >
              <el-option
                v-for="type in typeList"
                :key="type.typeId"
                :label="type.typeName"
                :value="type.typeId"
              >
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="题目信息" prop="questionTitle">
            <el-input
              type="textarea"
              :autosize="{ minRows: 2 }"
              v-model="questionForm.questionTitle"
              style="width: 250px"
              maxlength="50"
              show-word-limit
            ></el-input>
          </el-form-item>
          <div v-if="questionForm.typeId == '1'">
            <el-form-item
              v-for="(option, index) in questionForm.answer"
              :key="option.answerId"
              :label="'选项' + number2Letter(index)"
              :prop="'answer[' + index + '].content'"
              :rules="{
                required: true,
                message: '请填写选项' + number2Letter(index) + '的内容',
                trigger: 'blur',
              }"
            >
              <el-input v-model="option.content" style="margin-right: 5px" />
              <el-button type="info" @click.prevent="removeOptions(option)"
                >-</el-button
              >
            </el-form-item>
          </div>
          <el-form-item
            label="正确答案"
            prop="correct"
            :rules="{
              required: questionForm.typeId != '3',
              message: '请选择正确答案',
              trigger: 'change',
            }"
            v-if="questionForm.typeId != undefined"
          >
            <el-select
              v-model="questionForm.correct"
              placeholder="请选择正确答案"
              v-if="questionForm.typeId == '1'"
            >
              <el-option
                v-for="(option, index) in questionForm.answer"
                :key="option.key"
                :label="number2Letter(index)"
                :value="number2Letter(index)"
              ></el-option>
            </el-select>
            <el-button-group v-if="questionForm.typeId == '2'">
              <el-button
                icon="check"
                round
                :type="isTrue"
                @click="changeJudge(true)"
              ></el-button>
              <el-button
                icon="close"
                round
                :type="isFalse"
                @click="changeJudge(false)"
              ></el-button>
            </el-button-group>
            <span style="color: red" v-if="questionForm.typeId == '3'">
              简答题默认无标准答案，并用相似度对比
            </span>
          </el-form-item>
        </el-form>
        <template #footer>
          <span class="dialog-footer">
            <el-button
              type="danger"
              v-if="questionForm.typeId == '1'"
              @click="addOptions(questionForm.answer.length)"
              >+ 选项</el-button
            >
            <el-button @click="dialogFormVisible = false">取 消</el-button>
            <el-button type="primary" @click="save()">确 定</el-button>
          </span>
        </template>
      </el-dialog>
    </div>
    <el-table
      :data="
        tableData.filter(
          (data) =>
            !search ||
            data.questionTitle.toLowerCase().includes(search.toLowerCase())
        )
      "
      border
      height="540px"
      @selection-change="handleSelectionChange"
    >
      <el-table-column type="selection" width="40"> </el-table-column>
      <el-table-column type="index" label="序号" width="80"> </el-table-column>
      <el-table-column
        prop="subjectName"
        label="所属科目"
        :filters="subjectFilterData"
        :filter-method="subjectFilter"
        width="150"
      >
      </el-table-column>
      <el-table-column
        prop="typeName"
        label="类型"
        :filters="typeFilterData"
        :filter-method="typeFilter"
        width="100"
      >
        <template #default="scope">
          <el-tag
            :type="
              scope.row.typeName == '判断题'
                ? 'success'
                : scope.row.typeName == '简答题'
                ? 'danger'
                : ''
            "
          >
            {{ scope.row.typeName }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="questionTitle" label="题目"> </el-table-column>
      <el-table-column prop="answer" label="正确答案" width="100">
        <template #default="scope">
          <el-popover
            trigger="hover"
            placement="left"
            v-if="scope.row.typeId == '1'"
          >
            <p v-for="option in scope.row.answer" :key="option.answerId">
              <span>选项{{ option.answerSign }}: {{ option.content }}</span>
            </p>
            <template #reference class="name-wrapper">
              <el-tag>{{ scope.row.correct }}</el-tag>
            </template>
          </el-popover>
          <el-tag v-if="scope.row.typeId == '2'">
            {{ scope.row.answer[0].answerSign == "1" ? "√" : "×" }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column width="300">
        <template #header>
          <el-input v-model="search" placeholder="输入题目信息进行搜索" />
        </template>
        <template #default="scope">
          <el-button
            @click="
              clearFormFields();
              this.status = '修改';
              dialogFormVisible = true;
              loadInfo(scope.row.questionId);
            "
            >编辑</el-button
          >
          <el-button type="danger" @click="del([scope.row])">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    <div style="margin-top: 10px">
      <el-pagination
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        :current-page="pageno"
        :page-sizes="[5, 10, 20, 50]"
        :page-size="size"
        layout="total, sizes, ->, pager, next, jumper"
        :total="totalItems"
      >
      </el-pagination>
    </div>
  </div>
</template>
<script>
import userToken from "@/services/auth-header";
import { dealSelect } from "@/services/response";
export default {
  data() {
    return {
      status: "",
      dialogFormVisible: false,
      userId: "",
      questionForm: {
        questionId: "",
        questionTitle: "",
        subjectId: "",
        subjectName: "",
        typeId: "",
        typeName: "",
        answer: [
          {
            answerId: "",
            answerSign: "",
            content: "",
            isCorrect: 0, // 1-是
          },
        ],
        correct: "",
      },
      formRules: {
        questionTitle: [
          { required: true, message: "请填写题目信息", trigger: "blur" },
        ],
        subjectId: [
          { required: true, message: "请选择所属科目", trigger: "change" },
        ],
        typeId: [
          { required: true, message: "请选择题目类型", trigger: "change" },
        ],
      },
      isTrue: "",
      isFalse: "",

      multiSelection: [],
      subjectList: [],
      subjectFilterData: [],
      typeList: [],
      typeFilterData: [],
      search: "",
      tableData: [],
      pageno: 1,
      size: 10,
      totalItems: 0,
    };
  },
  created() {
    this.userId = this.$storage.getStorageSync("user").id;
    this.loadData();
  },
  methods: {
    // 初始化页面
    loadData() {
      this.findAll();
      this.loadSubjectByTeacherId();
      this.loadQuestionType();
    },
    clearFormFields() {
      this.questionForm = {};
      this.questionForm.answer = [
        {
          answerId: "",
          answerSign: "A",
          content: "",
          isCorrect: 0,
        },
      ];
      this.$nextTick(() => {
        this.$refs.questionForm.clearValidate();
      });
      this.isTrue = "";
      this.isFalse = "";
    },
    number2Letter(num) {
      return String.fromCharCode("A".charCodeAt() + num);
    },

    findAll() {
      this.$axios
        .get("/question/findAllByTeacherId", {
          headers: { Authorization: userToken() },
          params: {
            userId: this.userId,
            pageno: this.pageno,
            size: this.size,
          },
        })
        .then((response) => {
          let res = dealSelect(response.data);
          if (res) {
            this.tableData = res.records;
            this.totalItems = res.total;
          }
        });
    },
    handleSizeChange(size) {
      this.size = size;
      this.pageno = 1;
      this.findAll();
    },
    handleCurrentChange(pageno) {
      this.pageno = pageno;
      this.findAll();
    },

    loadSubjectByTeacherId() {
      this.$axios
        .get("/subject/loadSubjectByTeacherId", {
          headers: { Authorization: userToken() },
          params: { teacherId: this.userId },
        })
        .then((response) => {
          let res = dealSelect(response.data);
          if (res) {
            this.subjectList = res;
            this.subjectFilterData = [];
            res.forEach((item) => {
              this.subjectFilterData.push({
                text: item.subjectName,
                value: item.subjectId,
              });
            });
          }
        });
    },
    subjectFilter(value, row) {
      return row.subjectId === value;
    },
    valueToSubjectId(val) {
      this.questionForm.subjectId = val;
    },

    loadQuestionType() {
      this.$axios
        .get("/questionType/loadQuestionType", {
          headers: { Authorization: userToken() },
        })
        .then((response) => {
          let res = dealSelect(response.data);
          if (res) {
            this.typeList = res;
            this.typeFilterData = [];
            res.forEach((item) => {
              this.typeFilterData.push({
                text: item.typeName,
                value: item.typeId,
              });
            });
          }
        });
    },
    typeFilter(value, row) {
      return row.typeId === value;
    },
    valueToTypeId(val) {
      this.questionForm.typeId = val;
      this.questionForm.answer = [
        {
          answerId: "",
          answerSign: "A",
          content: "",
          isCorrect: 0,
        },
      ];
    },

    addOptions(index) {
      this.questionForm.answer.push({
        answerId: "",
        answerSign: this.number2Letter(index),
        content: "",
        isCorrect: 0,
      });
    },
    removeOptions(item) {
      let index = this.questionForm.answer.indexOf(item);
      if (index != -1) {
        this.questionForm.answer.splice(index, 1);
        this.questionForm.correct = "";
      }
      // 更新选项
      this.questionForm.answer.forEach((item) => {
        item.answerSign = this.number2Letter(
          this.questionForm.answer.indexOf(item)
        );
      });
    },
    changeJudge(choose) {
      this.questionForm.correct = choose;
      this.isTrue = choose ? "primary" : "";
      this.isFalse = !choose ? "primary" : "";
    },

    // 新增&编辑
    loadInfo(id) {
      this.$axios
        .get("/question/findById", {
          headers: { Authorization: userToken() },
          params: { questionId: id },
        })
        .then((response) => {
          let res = dealSelect(response.data);
          if (res) {
            this.questionForm = res;
            if (this.questionForm.typeId == "2") {
              this.changeJudge(this.questionForm.correct == "1");
            }
          }
        });
    },
    save() {
      this.$refs.questionForm.validate((valid) => {
        if (valid) {
          let data = {
            questionId: this.questionForm.questionId,
            questionTitle: this.questionForm.questionTitle,
            subjectId: this.questionForm.subjectId,
            typeId: this.questionForm.typeId,
            correct: this.questionForm.correct,
            status: this.status,
          };
          let option = [""];
          if (this.questionForm.typeId == "1") {
            // choice
            this.questionForm.answer.forEach((item) => {
              option.push(
                (item.answerId != "" ? item.answerId : "") +
                  " " +
                  item.answerSign +
                  " " +
                  item.content
              );
            });
          }
          data.correct += option;
          this.$axios
            .post(
              "/question/save",
              this.$qs.stringify(data, { indices: false }),
              { headers: { Authorization: userToken() } }
            )
            .then((response) => {
              this.dialogFormVisible = false;
              this.$message({
                type: response.data.success ? "success" : "error",
                message: response.data.message,
              });
              this.loadData();
            })
            .catch(function (error) {
              this.$message.info("数据出错");
              console.log(error);
            });
        }
      });
    },

    // 删除
    handleSelectionChange(val) {
      this.multiSelection = val;
    },
    del(arr) {
      if (arr.length) {
        this.$confirm("此操作将永久删除信息, 是否继续？", "提示", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning",
        })
          .then(() => {
            let params = [];
            arr.forEach(function (item) {
              params.push(item.questionId);
            });
            this.$axios
              .post(
                "/question/delete",
                this.$qs.stringify({ questionId: params }, { indices: false }),
                { headers: { Authorization: userToken() } }
              )
              .then((response) => {
                this.$message({
                  type: response.data.success ? "success" : "error",
                  message: response.data.message,
                });
                this.loadData();
              });
          })
          .catch(() => {
            this.$message.info("已取消删除");
          });
      } else {
        this.$message.info("请选择要删除的信息");
      }
    },
  },
};
</script>
<style scoped>
.el-input {
  width: 250px;
}
</style>