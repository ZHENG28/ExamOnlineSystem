<template>
  <div>
    <div style="margin-bottom: 10px">
      <h2 style="display: inline">科目信息</h2>
      <div style="display: inline; margin-left: 10px">
        <el-select
          filterable
          placeholder="请选择专业"
          @change="valueToMajorId"
          v-model="majorForm.majorName"
        >
          <el-option
            v-for="major in majorList"
            :key="major.majorId"
            :label="major.majorName"
            :value="major.majorId"
          >
          </el-option>
        </el-select>
      </div>
      <div style="float: right">
        <el-button
          @click="
            clearFormFields();
            this.status = '新增';
            dialogFormVisible = true;
          "
          :disabled="majorForm.majorName == ''"
          >新增</el-button
        >
        <el-button
          type="danger"
          @click="del(this.multiSelection)"
          :disabled="majorForm.majorName == ''"
          >删除</el-button
        >
      </div>
      <el-dialog
        :title="status + '科目信息'"
        v-model="dialogFormVisible"
        width="600px"
      >
        <el-form
          :model="subjectForm"
          :rules="formRules"
          ref="subjectForm"
          label-width="200px"
          label-position="right"
        >
          <el-form-item label="科目名称" prop="subjectName">
            <el-input v-model="subjectForm.subjectName"></el-input>
          </el-form-item>
          <el-form-item label="授课教师" prop="teacherId">
            <el-select
              filterable
              placeholder="请选择授课教师"
              @change="valueToUserId"
              v-model="subjectForm.teacherId"
            >
              <el-option
                v-for="teacher in teacherList"
                :key="teacher.userId"
                :label="teacher.username"
                :value="teacher.userId"
              >
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="授课班级" prop="clazzId">
            <el-select
              filterable
              placeholder="请选择授课班级"
              @change="valueToClazzId"
              v-model="subjectForm.clazzId"
            >
              <el-option
                v-for="clazz in clazzList"
                :key="clazz.clazzId"
                :label="clazz.clazzName"
                :value="clazz.clazzId"
              >
              </el-option>
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
            data.subjectName.toLowerCase().includes(search.toLowerCase()) ||
            data.teacherName.toLowerCase().includes(search.toLowerCase())
        )
      "
      border
      height="540px"
      @selection-change="handleSelectionChange"
    >
      <el-table-column type="selection" width="40"> </el-table-column>
      <el-table-column type="index" label="序号" width="80"> </el-table-column>
      <el-table-column prop="subjectName" label="科目名称"> </el-table-column>
      <el-table-column prop="teacherName" label="授课教师"> </el-table-column>
      <el-table-column
        prop="clazzName"
        label="授课班级"
        :filters="clazzFilterData"
        :filter-method="clazzFilter"
      >
      </el-table-column>
      <el-table-column width="300">
        <template #header>
          <el-input
            v-model="search"
            placeholder="输入科目名或教师姓名进行搜索"
          />
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
import userToken from "@/services/auth-header";
import { dealSelect } from "@/services/response";
export default {
  data() {
    return {
      status: "",
      dialogFormVisible: false,
      subjectForm: {
        subjectId: "",
        subjectName: "",
        teacherId: "",
        teacherName: "",
        clazzId: "",
        clazzName: "",
      },
      formRules: {
        subjectName: [
          { required: true, message: "请填写科目", trigger: "blur" },
        ],
        teacherId: [
          { required: true, message: "请选择授课教师", trigger: "change" },
        ],
        clazzId: [
          { required: true, message: "请选择授课班级", trigger: "change" },
        ],
      },

      multiSelection: [],
      majorForm: {
        majorId: 0,
        majorName: "",
      },
      majorList: [],
      teacherList: [],
      clazzList: [],
      clazzFilterData: [],
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
      this.loadMajorList().then((response) => {
        this.findAll();
      });
    },
    clearFormFields() {
      this.subjectForm = {};
      this.$nextTick(() => {
        this.$refs.subjectForm.clearValidate();
      });
    },

    findAll() {
      this.$axios
        .get("/subject/findAllByMajorId", {
          headers: { Authorization: userToken() },
          params: {
            majorId: this.majorForm.majorId,
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

    loadMajorList() {
      return this.$axios
        .get("/major/findAll", { headers: { Authorization: userToken() } })
        .then((response) => {
          let res = dealSelect(response.data);
          if (res) {
            this.majorList = res;
          }
        });
    },
    valueToMajorId(val) {
      this.majorForm.majorId = val;
      this.findAll();
      this.loadTeacherByMajorId();
      this.loadClazzByMajorId();
    },

    loadTeacherByMajorId() {
      this.$axios
        .get("/user/loadTeacherByMajorId", {
          headers: { Authorization: userToken() },
          params: { majorId: this.majorForm.majorId },
        })
        .then((response) => {
          let res = dealSelect(response.data);
          if (res) {
            this.teacherList = res;
          }
        });
    },
    valueToUserId(val) {
      this.subjectForm.teacherId = val;
    },

    loadClazzByMajorId() {
      this.$axios
        .get("/clazz/loadClazzByMajorId", {
          headers: { Authorization: userToken() },
          params: { majorId: this.majorForm.majorId },
        })
        .then((response) => {
          let res = dealSelect(response.data);
          if (res) {
            this.clazzList = res;
            this.clazzFilterData = [];
            res.forEach((item) => {
              this.clazzFilterData.push({
                text: item.clazzName,
                value: item.clazzId,
              });
            });
          }
        });
    },
    clazzFilter(value, row) {
      return row.clazzId === value;
    },
    valueToClazzId(val) {
      this.subjectForm.clazzId = val;
    },

    // 新增&编辑
    loadInfo(id) {
      this.$axios
        .get("/subject/findById", {
          headers: { Authorization: userToken() },
          params: { subjectId: id },
        })
        .then((response) => {
          let res = dealSelect(response.data);
          if (res) {
            this.subjectForm = res;
          }
        });
    },
    save() {
      this.$refs.subjectForm.validate((valid) => {
        if (valid) {
          this.$axios
            .post(
              "/subject/save",
              this.$qs.stringify({
                subjectId: this.subjectForm.subjectId,
                subjectName: this.subjectForm.subjectName,
                teacherId: this.subjectForm.teacherId,
                clazzId: this.subjectForm.clazzId,
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
              params.push(item.subjectId);
            });
            this.$axios
              .post(
                "/subject/delete",
                this.$qs.stringify({ subjectId: params }, { indices: false }),
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