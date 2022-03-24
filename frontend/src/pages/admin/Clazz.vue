<template>
  <div class="row">
    <div class="col-12">
      <div>
        <h3 style="display: inline">班级信息</h3>
        <div style="float: right">
          <el-button
            @click="
              clearFormFields();
              dialogFormVisible = true;
            "
            >添加</el-button
          >
          <el-dialog title="添加班级信息" :visible.sync="dialogFormVisible">
            <el-form :model="clazzForm" :rules="FormRules" ref="clazzForm">
              <el-form-item
                label="专业"
                :label-width="formLabelWidth"
                prop="major"
              >
                <el-input
                  v-model="clazzForm.major"
                  style="width: 250px"
                ></el-input>
              </el-form-item>
              <el-form-item
                label="班级"
                :label-width="formLabelWidth"
                prop="clazzName"
              >
                <el-input
                  v-model="clazzForm.clazzName"
                  style="width: 250px"
                ></el-input>
              </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
              <el-button @click="dialogFormVisible = false">取 消</el-button>
              <el-button type="primary" @click="add()">确 定</el-button>
            </div>
          </el-dialog>
          <el-button
            type="danger"
            @click="multidel()"
            :disabled="false"
            >删除</el-button
          >
        </div>
      </div>
      <el-table
        :data="
          tableData.filter(
            (data) =>
              !search ||
              data.major.toLowerCase().includes(search.toLowerCase()) ||
              data.clazzName.toLowerCase().includes(search.toLowerCase())
          )
        "
        height="480px"
        border
        style="width: 100%"
        @selection-change="handleSelectionChange"
      >
        <el-table-column type="selection" width="40"> </el-table-column>
        <el-table-column sortable type="index" label="编号" width="50">
        </el-table-column>
        <el-table-column prop="major" label="专业"> </el-table-column>
        <el-table-column prop="clazzName" label="班级"> </el-table-column>
        <el-table-column label="搜索">
          <!-- eslint-disable-next-line -->
          <template slot="header" slot-scope="scope">
            <el-input
              v-model="search"
              size="mini"
              placeholder="输入专业或班级进行搜索"
            />
          </template>
          <template slot-scope="scope">
            <el-button
              @click="
                loadModify(scope.row);
                modifyFormVisible = true;
              "
              >编辑</el-button
            >
            <el-dialog title="修改班级信息" :visible.sync="modifyFormVisible">
              <el-form :model="clazzForm" :rules="FormRules">
                <el-form-item label="专业" :label-width="formLabelWidth">
                  <el-input
                    v-model="clazzForm.major"
                    style="width: 250px"
                  ></el-input>
                </el-form-item>
                <el-form-item label="班级" :label-width="formLabelWidth">
                  <el-input
                    v-model="clazzForm.clazzName"
                    style="width: 250px"
                  ></el-input>
                </el-form-item>
              </el-form>
              <div slot="footer" class="dialog-footer">
                <el-button @click="modifyFormVisible = false">取 消</el-button>
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
      search: "",
      multipleSelection: [],
      modifyFormVisible: false,
      dialogFormVisible: false,
      clazzForm: {
        clazzId: "",
        major: "",
        clazzName: "",
      },
      formLabelWidth: "200px",
      FormRules: {
        major: [{ required: true, message: "请填写专业名称", trigger: "blur" }],
        clazzName: [
          { required: true, message: "请填写班级名称", trigger: "blur" },
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
    handleSizeChange(size) {
      this.$data.size = size;
      this.findAll();
    },
    handleCurrentChange(pageno) {
      this.$data.pageno = pageno - 1;
      this.findAll();
    },
    clearFormFields() {
      this.$data.clazzForm = {
        clazzId: "",
        major: "",
        clazzName: "",
      };
    },

    loadData() {
      this.findAll();
    },
    findAll() {
      this.$ajax
        .post(
          "/clazz/findAll",
          this.$qs.stringify({
            pageno: this.$data.pageno,
            size: this.$data.size,
          }),
          { headers: authHeader() }
        )
        .then((response) => {
          this.$data.tableData = response.data.content;
          this.$data.totalItems = response.data.totalElements;
        });
    },

    add() {
      this.$refs["clazzForm"].validate((valid) => {
        if (!valid) {
          console.log("error submit!!");
          return;
        } else {
          this.$ajax
            .get("/clazz/add", {
              headers: authHeader(),
              params: {
                clazzId: this.clazzForm.clazzId,
                major: this.clazzForm.major,
                clazzName: this.clazzForm.clazzName,
              },
            })
            .then((response) => {
              this.$data.dialogFormVisible = false;
              if (response.data == "success") {
                this.clearFormFields();
                this.$message.success("添加成功");
                this.loadData();
              } else if (response.data == "failed") {
                this.$message.error("添加失败");
              }
            })
            .catch(function (error) {
              this.$message.info("数据出错");
              console.log(error);
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
              "/clazz/del",
              this.$qs.stringify({
                clazzId: row.clazzId,
                pageno: this.$data.pageno,
                size: this.$data.size,
              }),
              { headers: authHeader() }
            )
            .then((response) => {
              this.$data.tableData = response.data.content;
              this.$data.totalItems = response.data.totalElements;
              this.$message.success("删除成功!");
            })
            .catch(() => {
              this.$message.error("删除失败");
            });
        })
        .catch(() => {
          this.$message.info("已取消删除");
        });
    },
    // 表格的筛选器和多选框存在冲突
    handleSelectionChange(val) {
      this.multipleSelection = val;
    },
    multidel() {
      this.$confirm("此操作将永久删除信息, 是否继续?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(() => {
          let checkArr = this.$data.multiSelection;
          let params = [];
          checkArr.forEach(function (item) {
            params.push(item.clazzId);
          });
          this.$ajax
            .post(
              "/clazz/del",
              this.$qs.stringify(
                {
                  clazzId: params,
                  pageno: this.$data.pageno,
                  size: this.$data.size,
                },
                { indices: false }
              ),
              { headers: authHeader() }
            )
            .then((response) => {
              this.$data.tableData = response.data.content;
              this.$data.totalItems = response.data.totalElements;
              this.$message.success("删除成功!");
            })
            .catch(() => {
              this.$message.error("删除失败");
            });
        })
        .catch(() => {
          this.$message.info("已取消删除");
        });
    },

    loadModify(row) {
      this.$ajax
        .post("/clazz/findById", this.$qs.stringify({ clazzId: row.clazzId }), {
          headers: authHeader(),
        })
        .then((res) => {
          let clazz = res.data;
          let Form = this.$data.clazzForm;
          Form.clazzId = clazz.clazzId;
          Form.major = clazz.major;
          Form.clazzName = clazz.clazzName;
        });
    },
    modify() {
      if (this.clazzForm.clazzName == "") {
        this.$message.warning("请填写班级名称");
        return;
      }
      this.$ajax
        .post(
          "/clazz/modify",
          this.$qs.stringify({
            clazzId: this.clazzForm.clazzId,
            major: this.clazzForm.major,
            clazzName: this.clazzForm.clazzName,
          }),
          { headers: authHeader() }
        )
        .then((response) => {
          this.$data.modifyFormVisible = false;
          if (response.data == "success") {
            this.$message.success("修改成功");
            this.loadData();
          } else if (response.data == "failed") {
            this.$message.error("修改失败");
          }
          this.clearFormFields();
        })
        .catch(function (error) {
          this.$message.info("数据出错");
          console.log(error);
        });
    },
  },
};
</script>
<style>
</style>