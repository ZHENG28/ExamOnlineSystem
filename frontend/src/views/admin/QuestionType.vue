<template>
  <div>
    <div style="margin-bottom: 10px">
      <h2 style="display: inline">问题类型信息</h2>
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
        :title="status + '问题类型'"
        v-model="dialogFormVisible"
        width="600px"
      >
        <el-form
          :model="typeForm"
          :rules="formRules"
          ref="typeForm"
          label-width="200px"
          label-position="right"
        >
          <el-form-item label="名称" prop="typeName">
            <el-input v-model="typeForm.typeName"></el-input>
          </el-form-item>
          <el-form-item label="描述" prop="description">
            <el-input
              type="textarea"
              :autosize="{ minRows: 2 }"
              v-model="typeForm.description"
              style="width: 250px"
              maxlength="100"
              show-word-limit
            ></el-input>
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
            data.typeName.toLowerCase().includes(search.toLowerCase())
        )
      "
      border
      height="540px"
      @selection-change="handleSelectionChange"
    >
      <el-table-column type="selection" width="40"> </el-table-column>
      <el-table-column type="index" label="序号" width="60"> </el-table-column>
      <el-table-column prop="typeName" label="名称" width="200">
      </el-table-column>
      <el-table-column prop="description" label="描述"> </el-table-column>
      <el-table-column width="300">
        <template #header>
          <el-input v-model="search" placeholder="输入类型名称进行搜索" />
        </template>
        <template #default="scope">
          <el-button
            @click="
              clearFormFields();
              this.status = '修改';
              dialogFormVisible = true;
              loadInfo(scope.row.typeId);
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
      typeForm: {
        typeId: "",
        typeName: "",
        description: "",
      },
      formRules: {
        typeName: [
          { required: true, message: "请填写类型名称", trigger: "blur" },
        ],
      },

      multiSelection: [],
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
    // 初始化页面
    loadData() {
      this.findAll();
    },
    clearFormFields() {
      this.typeForm = {};
      this.$nextTick(() => {
        this.$refs.typeForm.clearValidate();
      });
    },

    findAll() {
      this.$axios
        .get("/questionType/findAll", {
          headers: { Authorization: userToken() },
          params: {
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

    // 新增&编辑
    loadInfo(id) {
      this.$axios
        .get("/questionType/findById", {
          headers: { Authorization: userToken() },
          params: { typeId: id },
        })
        .then((response) => {
          let res = dealSelect(response.data);
          if (res) {
            this.typeForm = res;
          }
        });
    },
    save() {
      this.$refs.typeForm.validate((valid) => {
        if (valid) {
          this.$axios
            .post(
              "/questionType/save",
              this.$qs.stringify({
                typeId: this.typeForm.typeId,
                typeName: this.typeForm.typeName,
                description: this.typeForm.description,
                status: this.status,
              }),
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
              params.push(item.typeId);
            });
            this.$axios
              .post(
                "/questionType/delete",
                this.$qs.stringify({ typeId: params }, { indices: false }),
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