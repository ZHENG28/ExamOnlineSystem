<template>
  <div>
    <div style="margin-bottom: 10px">
      <h2 style="display: inline">科目信息</h2>
      <div style="float: right">
        <el-button
          @click="
            clearFormFields();
            this.status = '新增';
            dialogFormVisible = true;
            findAllMajorAndClazz();
          "
          >新增</el-button
        >
        <el-button type="danger" @click="del(this.multiSelection)"
          >删除</el-button
        >
      </div>
      <el-dialog
        :title="status + '科目信息'"
        v-model="dialogFormVisible"
        width="600px"
      >
        <el-form
          :model="subForm"
          :rules="formRules"
          ref="subForm"
          label-width="200px"
          label-position="right"
        >
          <el-form-item label="科目名称" prop="subjectName">
            <el-input v-model="subForm.subjectName"></el-input>
          </el-form-item>
          <el-form-item label="授课班级" prop="clazzId">
            <el-cascader
              @change="valueToClazzId"
              v-model="majorclazzName"
              placeholder="请选择授课班级"
              :options="clazzList"
              filterable
              :show-all-levels="false"
              :props="{ expandTrigger: 'hover' }"
            ></el-cascader>
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
            data.subjectName.toLowerCase().includes(search.toLowerCase())
        )
      "
      border
      height="540px"
      @selection-change="handleSelectionChange"
    >
      <el-table-column type="selection" width="40"> </el-table-column>
      <el-table-column type="index" label="序号" width="80"> </el-table-column>
      <el-table-column prop="subjectName" label="科目名称"> </el-table-column>
      <el-table-column
        prop="clazzName"
        label="授课班级"
        :filters="clazzNameFilterData"
        :filter-method="clazzNameFilter"
      >
        <template #default="scope">
          {{ scope.row.clazzName }}
        </template>
      </el-table-column>
      <el-table-column width="300">
        <template #header>
          <el-input v-model="search" placeholder="输入科目名进行搜索" />
        </template>
        <template #default="scope">
          <el-button
            @click="
              clearFormFields();
              this.status = '修改';
              dialogFormVisible = true;
              loadInfo(scope.row.subjectId);
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
import authHeader from "@/services/auth-header";
export default {
  data() {
    return {
      status: "",
      dialogFormVisible: false,
      clazzList: [],
      majorclazzName: [],
      userId: "",
      subForm: {
        subjectId: "",
        subjectName: "",
        clazzId: "",
        clazzName: "",
      },
      formRules: {
        subjectName: [
          { required: true, message: "请填写科目", trigger: "blur" },
        ],
        clazzId: [
          { required: true, message: "请选择授课班级", trigger: "blur" },
        ],
      },

      multiSelection: [],
      clazzNameFilterData: [],
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
      this.findDistinctClazzName();
    },
    clearFormFields() {
      this.subForm = {};
      this.$nextTick(() => {
        this.$refs.subForm.clearValidate();
      });
      this.majorclazzName = [];
    },

    findAll() {
      this.$axios
        .post(
          "/subject/findAllByTchIdOrNot",
          this.$qs.stringify({
            userId: this.userId,
            // userId: "4",
            pageno: this.pageno,
            size: this.size,
          }),
          { headers: authHeader() }
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

    findDistinctClazzName() {
      this.$axios
        .get("/clazz/getDistinctClazz", { headers: authHeader() })
        .then((response) => {
          this.clazzNameFilterData = response.data;
        });
    },
    clazzNameFilter(value, row) {
      return row.clazzName === value;
    },

    findAllMajorAndClazz() {
      this.$axios
        .get("/clazz/findAllMajorAndClazz", { headers: authHeader() })
        .then((response) => {
          this.clazzList = response.data;
        });
    },
    valueToClazzId(row) {
      this.subForm.clazzId = row[1];
    },

    // 新增&编辑
    loadInfo(id) {
      this.$axios
        .post("/subject/findById", this.$qs.stringify({ subjectId: id }), {
          headers: authHeader(),
        })
        .then((response) => {
          this.subForm = response.data;
          this.findAllMajorAndClazz();
          this.majorclazzName = [response.data.major, response.data.clazzId];
        });
    },
    save() {
      this.$refs.subForm.validate((valid) => {
        if (valid) {
          this.$axios
            .get("/subject/save", {
              headers: authHeader(),
              params: {
                subjectId: this.subForm.subjectId,
                subjectName: this.subForm.subjectName,
                teacherId: this.userId,
                // teacherId: "4",
                clazzId: this.subForm.clazzId,
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
              params.push(item.subjectId);
            });
            this.$axios
              .post(
                "/subject/del",
                this.$qs.stringify(
                  {
                    subjectId: params,
                    pageno: this.pageno,
                    size: this.size,
                  },
                  { indices: false }
                ),
                { headers: authHeader() }
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