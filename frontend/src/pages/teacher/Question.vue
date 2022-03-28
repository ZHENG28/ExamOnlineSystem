<template>
  <div class="row">
    <div class="col-12">
      <div>
        <h3 style="display: inline">题目信息</h3>
        <div style="float: right">
          <el-button
            @click="
              clearFormFields();
              dialogFormVisible = true;
            "
            >添加</el-button
          >
          <el-dialog title="添加题目信息" :visible.sync="dialogFormVisible">
            <el-form :model="quesForm" :rules="FormRules" ref="quesForm">
              <el-form-item
                label="所属科目"
                :label-width="formLabelWidth"
                prop="sub.subName"
              >
                <el-select
                  filterable
                  placeholder="请选择科目"
                  @change="valueTosubId"
                  v-model="quesForm.sub.subName"
                >
                  <el-option
                    v-for="sub in subArr"
                    :key="sub.subId"
                    :label="sub.text"
                    :value="sub.subId"
                  >
                  </el-option>
                </el-select> </el-form-item
              ><el-form-item
                label="题目信息"
                :label-width="formLabelWidth"
                prop="quesTitle"
              >
                <el-input
                  v-model="quesForm.quesTitle"
                  style="width: 250px"
                ></el-input> </el-form-item
              ><el-form-item
                label="选项A"
                :label-width="formLabelWidth"
                prop="optA"
              >
                <el-input
                  v-model="quesForm.optA"
                  style="width: 250px"
                ></el-input> </el-form-item
              ><el-form-item
                label="选项B"
                :label-width="formLabelWidth"
                prop="optB"
              >
                <el-input
                  v-model="quesForm.optB"
                  style="width: 250px"
                ></el-input> </el-form-item
              ><el-form-item
                label="选项C"
                :label-width="formLabelWidth"
                prop="optC"
              >
                <el-input
                  v-model="quesForm.optC"
                  style="width: 250px"
                ></el-input> </el-form-item
              ><el-form-item
                label="选项D"
                :label-width="formLabelWidth"
                prop="optD"
              >
                <el-input
                  v-model="quesForm.optD"
                  style="width: 250px"
                ></el-input> </el-form-item
              ><el-form-item
                label="正确答案"
                :label-width="formLabelWidth"
                prop="answer"
              >
                <el-select
                  v-model="quesForm.answer"
                  placeholder="请选择正确答案"
                >
                  <el-option label="A" value="A"></el-option>
                  <el-option label="B" value="B"></el-option>
                  <el-option label="C" value="C"></el-option>
                  <el-option label="D" value="D"></el-option>
                </el-select>
              </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
              <el-button
                @click="
                  clearFormFields();
                  dialogFormVisible = false;
                "
                >取 消</el-button
              >
              <el-button type="primary" @click="add()">确 定</el-button>
            </div>
          </el-dialog>
          <el-button type="danger" @click="multidel()" :disabled="false"
            >删除</el-button
          >
        </div>
      </div>
      <el-table
        :data="
          tableData.filter(
            (data) =>
              !search ||
              data.quesTitle.toLowerCase().includes(search.toLowerCase())
          )
        "
        height="480px"
        border
        style="width: 100%"
        @selection-change="handleSelectionChange"
      >
        <el-table-column type="selection" width="40"> </el-table-column>
        <el-table-column type="index" label="序号" width="50"></el-table-column>
        <el-table-column
          prop="sub.subName"
          label="所属科目"
          :filters="subArr"
          :filter-method="filterSubId"
          filter-placement="bottom-end"
        >
        </el-table-column>
        <el-table-column prop="quesTitle" label="题目"> </el-table-column>
        <el-table-column prop="answer" label="正确答案">
          <template slot-scope="scope">
            <el-popover trigger="hover" placement="top">
              <p>选项A: {{ scope.row.optA }}</p>
              <p>选项B: {{ scope.row.optB }}</p>
              <p>选项C: {{ scope.row.optC }}</p>
              <p>选项D: {{ scope.row.optD }}</p>
              <div slot="reference" class="name-wrapper">
                <el-tag size="medium">{{ scope.row.answer }}</el-tag>
              </div>
            </el-popover>
          </template>
        </el-table-column>
        <el-table-column label="搜索">
          <!-- eslint-disable-next-line -->
          <template slot="header" slot-scope="scope">
            <el-input
              v-model="search"
              size="mini"
              placeholder="输入题目信息进行搜索"
            />
          </template>
          <template slot-scope="scope">
            <el-button
              @click="
                clearFormFields();
                loadModify(scope.row);
                modifyFormVisible = true;
              "
              >编辑</el-button
            >
            <el-dialog title="修改题目信息" :visible.sync="modifyFormVisible">
              <el-form :model="quesForm" :rules="FormRules">
                <el-form-item label="所属科目" :label-width="formLabelWidth">
                  <el-select
                    v-model="quesForm.sub.subName"
                    filterable
                    placeholder="请选择科目"
                    @change="valueTosubId"
                  >
                    <el-option
                      v-for="sub in subArr"
                      :key="sub.subId"
                      :label="sub.text"
                      :value="sub.subId"
                    >
                    </el-option>
                  </el-select> </el-form-item
                ><el-form-item label="题目信息" :label-width="formLabelWidth">
                  <el-input
                    v-model="quesForm.quesTitle"
                    style="width: 250px"
                  ></el-input> </el-form-item
                ><el-form-item label="选项A" :label-width="formLabelWidth">
                  <el-input
                    v-model="quesForm.optA"
                    style="width: 250px"
                  ></el-input> </el-form-item
                ><el-form-item label="选项B" :label-width="formLabelWidth">
                  <el-input
                    v-model="quesForm.optB"
                    style="width: 250px"
                  ></el-input> </el-form-item
                ><el-form-item label="选项C" :label-width="formLabelWidth">
                  <el-input
                    v-model="quesForm.optC"
                    style="width: 250px"
                  ></el-input> </el-form-item
                ><el-form-item label="选项D" :label-width="formLabelWidth">
                  <el-input
                    v-model="quesForm.optD"
                    style="width: 250px"
                  ></el-input> </el-form-item
                ><el-form-item label="正确答案" :label-width="formLabelWidth">
                  <el-select
                    v-model="quesForm.answer"
                    placeholder="请选择正确答案"
                  >
                    <el-option label="A" value="A"></el-option>
                    <el-option label="B" value="B"></el-option>
                    <el-option label="C" value="C"></el-option>
                    <el-option label="D" value="D"></el-option>
                  </el-select>
                </el-form-item>
              </el-form>
              <div slot="footer" class="dialog-footer">
                <el-button
                  @click="
                    clearFormFields();
                    modifyFormVisible = false;
                  "
                  >取 消</el-button
                >
                <el-button type="primary" @click="modify()">确 定</el-button>
              </div>
            </el-dialog>
            <el-button type="danger" @click="del(scope.row)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
      <div class="block">
        <el-pagination
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
          :current-page="pageno + 1"
          :page-sizes="[5, 10, 20, 50]"
          :page-size="size"
          layout="total, sizes, ->, pager, next, jumper"
          :total="totalItems"
        >
        </el-pagination>
      </div>
    </div>
  </div>
</template>
<script>
import authHeader from "@/services/auth-header";
export default {
  data() {
    return {
      tableData: [],
      subArr: [],
      search: "",
      multipleSelection: [],
      multipleSelectionFlag: true,
      dialogTableVisible: false,
      modifyFormVisible: false,
      dialogFormVisible: false,
      quesForm: {
        quesId: "",
        sub: {
          subId: "",
          subName: "",
        },
        quesTitle: "",
        optA: "",
        optB: "",
        optC: "",
        optD: "",
        answer: "",
      },
      formLabelWidth: "200px",
      FormRules: {
        subName: [
          { required: false, message: "请选择科目", trigger: "change" },
        ],
        quesTitle: [
          { required: true, message: "请填写题目信息", trigger: "blur" },
        ],
        optA: [
          { required: true, message: "请填写选项A的内容", trigger: "blur" },
        ],
        optB: [
          { required: true, message: "请填写选项B的内容", trigger: "blur" },
        ],
        optC: [
          { required: true, message: "请填写选项C的内容", trigger: "blur" },
        ],
        optD: [
          { required: true, message: "请填写选项D的内容", trigger: "blur" },
        ],
        answer: [
          { required: true, message: "请选择正确答案", trigger: "blur" },
        ],
      },
      pageno: 0,
      size: 10,
      totalItems: 0,
    };
  },
  created() {
    this.loadData();
  },
  methods: {
    filterSubId(value, row) {
      return value == row.sub.subName;
    },
    handleSizeChange(size) {
      this.size = size;
      this.findAll();
    },
    handleCurrentChange(pageno) {
      this.pageno = pageno - 1;
      this.findAll();
    },
    loadData() {
      this.findAll();
      this.$ajax
        .post("/singleQues/findAllSubId", {}, { headers: authHeader() })
        .then((response) => {
          this.subArr = response.data;
        });
    },
    valueTosubId(val) {
      this.quesForm.sub.subId = val;
    },
    clearFormFields() {
      this.quesForm = {
        quesId: "",
        sub: {
          subId: "",
          subName: "",
        },
        quesTitle: "",
        optA: "",
        optB: "",
        optC: "",
        optD: "",
        answer: "",
      };
    },
    findAll() {
      this.$ajax
        .post(
          "/singleQues/findAll",
          this.$qs.stringify({
            pageno: this.pageno,
            size: this.size,
          }),
          { headers: authHeader() }
        )
        .then((response) => {
          this.tableData = response.data.content;
          this.totalItems = response.data.totalElements;
        });
    },

    add() {
      this.$refs["quesForm"].validate((valid) => {
        if (!valid) {
          console.log("error submit!!");
          return;
        } else {
          this.$ajax
            .get("/singleQues/add", {
              headers: authHeader(),
              params: {
                quesId: this.quesForm.quesId,
                sub: this.quesForm.sub.subId,
                quesTitle: this.quesForm.quesTitle,
                optA: this.quesForm.optA,
                optB: this.quesForm.optB,
                optC: this.quesForm.optC,
                optD: this.quesForm.optD,
                answer: this.quesForm.answer,
              },
            })
            .then(
              (response) => {
                if (response.data == "success") {
                  this.$message.success("添加成功");
                  this.loadData();
                } else if (response.data == "failed") {
                  this.$message.error("添加失败");
                }
                this.clearFormFields();
                this.dialogFormVisible = false;
              },
              (error) => {
                console.log("error:" + error.message);
                this.clearFormFields();
                // this.$message.error("工号重复，请重新填写");
              }
            )
            .catch(function (error) {
              this.$message.info("数据出错");
              console.log(error);
              this.clearFormFields();
            });
        }
      });
    },

    del(row) {
      this.$confirm("此操作将永久删除信息, 是否继续?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(() => {
          this.$ajax
            .post(
              "/singleQues/del",
              this.$qs.stringify({
                quesId: row.quesId,
                pageno: this.pageno,
                size: this.size,
              }),
              { headers: authHeader() }
            )
            .then(
              (response) => {
                this.tableData = response.data.content;
                this.totalItems = response.data.totalElements;
                this.$message.success("删除成功!");
              },
              (error) => {
                console.log("error:" + error);
                this.$message.error("删除失败");
              }
            );
        })
        .catch(() => {
          this.$message.info("已取消删除");
        });
    },
    handleSelectionChange(val) {
      this.multipleSelection = val;
      if (this.multipleSelection.length == 0) {
        // this.multipleSelectionFlag = true;
      } else {
        // this.multipleSelectionFlag = false;
      }
    },
    multidel() {
      this.$confirm("此操作将永久删除信息, 是否继续?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(() => {
          let checkArr = this.multipleSelection;
          let params = [];
          checkArr.forEach(function (item) {
            params.push(item.quesId);
          });
          this.$ajax
            .post(
              "/singleQues/del",
              this.$qs.stringify(
                {
                  quesId: params,
                  pageno: this.pageno,
                  size: this.size,
                },
                { indices: false }
              ),
              { headers: authHeader() }
            )
            .then(
              (response) => {
                this.tableData = response.data.content;
                this.totalItems = response.data.totalElements;
                this.$message.success("删除成功!");
              },
              (error) => {
                console.log("error:" + error);
                this.$message.error("删除失败");
              }
            );
        })
        .catch(() => {
          this.$message.info("已取消删除");
        });
    },

    loadModify(row) {
      this.$ajax
        .post(
          "/singleQues/findById",
          this.$qs.stringify({ quesId: row.quesId }),
          { headers: authHeader() }
        )
        .then((res) => {
          let singleQues = res.data;
          let quesForm = this.quesForm;
          quesForm.quesId = singleQues.quesId;
          quesForm.quesTitle = singleQues.quesTitle;
          quesForm.optA = singleQues.optA;
          quesForm.optB = singleQues.optB;
          quesForm.optC = singleQues.optC;
          quesForm.optD = singleQues.optD;
          quesForm.answer = singleQues.answer;
          if (singleQues.sub != null) {
            quesForm.sub.subId = singleQues.sub.subId;
            quesForm.sub.subName = singleQues.sub.subName;
          }
        });
    },
    modify() {
      if (this.quesForm.subName == "") {
        this.$message.warning("请选择科目");
        return;
      }
      this.$ajax
        .post(
          "/singleQues/modify",
          this.$qs.stringify({
            quesId: this.quesForm.quesId,
            sub: this.quesForm.sub.subId,
            quesTitle: this.quesForm.quesTitle,
            optA: this.quesForm.optA,
            optB: this.quesForm.optB,
            optC: this.quesForm.optC,
            optD: this.quesForm.optD,
            answer: this.quesForm.answer,
          }),
          { headers: authHeader() }
        )
        .then(
          (response) => {
            if (response.data == "success") {
              this.$message.success("修改成功");
              this.loadData();
            } else if (response.data == "failed") {
              this.$message.error("修改失败");
            }
            this.clearFormFields();
            this.modifyFormVisible = false;
          },
          (error) => {
            console.log("error:" + error.message);
            // this.$message.error("工号重复，请重新填写");
          }
        )
        .catch(function (error) {
          this.$message.info("数据出错");
          console.log(error);
          this.clearFormFields();
        });
    },
  },
};
</script>
<style>
</style>