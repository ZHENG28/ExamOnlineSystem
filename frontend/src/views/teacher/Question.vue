<template>
  <div>
    <div style="margin-bottom: 10px">
      <h2 style="display: inline">题目信息</h2>
      <div style="float: right">
        <el-button
          @click="
            clearFormFields();
            this.status = '添加';
            dialogFormVisible = true;
          "
          >添加</el-button
        >
        <el-button
          type="danger"
          @click="del(this.multiSelection)"
          :disabled="false"
          >删除</el-button
        >
      </div>
      <el-dialog
        :title="status + '题目信息'"
        v-model="dialogFormVisible"
        width="600px"
      >
        <el-form
          :model="quesForm"
          :rules="formRules"
          ref="quesForm"
          label-width="200px"
          label-position="right"
        >
          <el-form-item label="所属科目" prop="subName">
            <el-select
              filterable
              placeholder="请选择科目"
              @change="valueTosubId"
              v-model="quesForm.subName"
            >
              <el-option
                v-for="sub in subIdFilterData"
                :key="sub.subId"
                :label="sub.text"
                :value="sub.subId"
              >
              </el-option>
            </el-select> </el-form-item
          ><el-form-item label="题目信息" prop="quesTitle">
            <el-input v-model="quesForm.quesTitle"></el-input> </el-form-item
          ><el-form-item label="选项A" prop="opta">
            <el-input v-model="quesForm.opta"></el-input> </el-form-item
          ><el-form-item label="选项B" prop="optb">
            <el-input v-model="quesForm.optb"></el-input> </el-form-item
          ><el-form-item label="选项C" prop="optc">
            <el-input v-model="quesForm.optc"></el-input> </el-form-item
          ><el-form-item label="选项D" prop="optd">
            <el-input v-model="quesForm.optd"></el-input> </el-form-item
          ><el-form-item label="正确答案" prop="answer">
            <el-select v-model="quesForm.answer" placeholder="请选择正确答案">
              <el-option label="A" value="A"></el-option>
              <el-option label="B" value="B"></el-option>
              <el-option label="C" value="C"></el-option>
              <el-option label="D" value="D"></el-option>
            </el-select>
          </el-form-item>
        </el-form>
        <template #footer>
          <span class="dialog-footer">
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
            data.quesTitle.toLowerCase().includes(search.toLowerCase())
        )
      "
      border
      height="540px"
      @selection-change="handleSelectionChange"
    >
      <el-table-column type="selection" width="40"> </el-table-column>
      <el-table-column type="index" label="序号" width="80"> </el-table-column>
      <el-table-column
        prop="subName"
        label="所属科目"
        :filters="subIdFilterData"
        :filter-method="subIdFilter"
      >
      </el-table-column>
      <el-table-column prop="quesTitle" label="题目"> </el-table-column>
      <el-table-column prop="answer" label="正确答案">
        <template #default="scope">
          <el-popover trigger="hover" placement="right">
            <p>选项A: {{ scope.row.opta }}</p>
            <p>选项B: {{ scope.row.optb }}</p>
            <p>选项C: {{ scope.row.optc }}</p>
            <p>选项D: {{ scope.row.optd }}</p>
            <template #reference class="name-wrapper">
              <el-tag>{{ scope.row.answer }}</el-tag>
            </template>
          </el-popover>
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
              loadInfo(scope.row.quesId);
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
// import authHeader from "@/services/auth-header";
export default {
  data() {
    return {
      multiSelection: [],
      status: "",
      dialogFormVisible: false,
      quesForm: {
        quesId: "",
        subId: "",
        subName: "",
        quesTitle: "",
        opta: "",
        optb: "",
        optc: "",
        optd: "",
        answer: "",
      },
      formRules: {
        subName: [{ required: true, message: "请选择科目", trigger: "blur" }],
        quesTitle: [
          { required: true, message: "请填写题目信息", trigger: "blur" },
        ],
        opta: [
          { required: true, message: "请填写选项A的内容", trigger: "blur" },
        ],
        optb: [
          { required: true, message: "请填写选项B的内容", trigger: "blur" },
        ],
        optc: [
          { required: true, message: "请填写选项C的内容", trigger: "blur" },
        ],
        optd: [
          { required: true, message: "请填写选项D的内容", trigger: "blur" },
        ],
        answer: [
          { required: true, message: "请选择正确答案", trigger: "blur" },
        ],
      },
      subIdFilterData: [],
      search: "",
      tableData: [],
      pageno: 1,
      size: 10,
      totalItems: 0,
    };
  },
  created() {
    this.loadData();
  },
  methods: {
    loadData() {
      this.findAll();
      this.findAllSubId();
    },
    clearFormFields() {
      this.quesForm = {};
    },

    findAll() {
      this.$axios
        .post(
          "/singleQuestion/findAll",
          this.$qs.stringify({
            pageno: this.pageno,
            size: this.size,
          })
          // { headers: authHeader() }
        )
        .then((response) => {
          this.tableData = response.data.records;
          this.totalItems = response.data.total;
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
    valueTosubId(val) {
      this.quesForm.subId = val;
    },

    findAllSubId() {
      this.$axios
        .post(
          "/subject/findAllSubId"
          //  { headers: authHeader() }
        )
        .then((response) => {
          this.subIdFilterData = response.data;
        });
    },
    subIdFilter(value, row) {
      return row.subName == value;
    },

    loadInfo(id) {
      this.$axios
        .post(
          "/singleQuestion/findById",
          this.$qs.stringify({ quesId: id })
          // { headers: authHeader(),}
        )
        .then((response) => {
          this.quesForm = response.data;
        });
    },
    save() {
      this.$refs.quesForm.validate((valid) => {
        if (valid) {
          this.$axios
            .get("/singleQuestion/save", {
              // headers: authHeader(),
              params: {
                quesId: this.quesForm.quesId,
                subId: this.quesForm.subId,
                quesTitle: this.quesForm.quesTitle,
                opta: this.quesForm.opta,
                optb: this.quesForm.optb,
                optc: this.quesForm.optc,
                optd: this.quesForm.optd,
                answer: this.quesForm.answer,
              },
            })
            .then((response) => {
              this.dialogFormVisible = false;
              if (response.data) {
                this.$message.success(this.status + "成功");
                this.loadData();
              } else {
                this.$message.error(this.status + "失败");
              }
            })
            .catch(function (error) {
              this.$message.info("数据出错");
              console.log(error);
            });
        }
      });
    },

    handleSelectionChange(val) {
      this.multiSelection = val;
    },
    del(arr) {
      this.$confirm("此操作将永久删除信息, 是否继续？", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(() => {
          let params = [];
          arr.forEach(function (item) {
            params.push(item.quesId);
          });
          this.$axios
            .post(
              "/singleQuestion/del",
              this.$qs.stringify(
                {
                  quesId: params,
                  pageno: this.pageno,
                  size: this.size,
                },
                { indices: false }
              )
              // { headers: authHeader() }
            )
            .then((response) => {
              this.tableData = response.data.records;
              this.totalItems = response.data.total;
              this.$message.success("删除成功！");
            })
            .catch(() => {
              this.$message.error("删除失败");
            });
        })
        .catch(() => {
          this.$message.info("已取消删除");
        });
    },
  },
};
</script>
<style scoped>
.el-input {
  width: 250px;
}
</style>