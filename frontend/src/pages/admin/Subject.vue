<template>
  <div class="row">
    <div class="col-12">
      <div>
        <h3 style="display: inline">科目信息</h3>
        <div style="float: right">
          <el-button
            @click="
              clearFormFields();
              findAllTch();
              dialogFormVisible = true;
            "
            >添加</el-button
          >
          <el-dialog title="添加科目信息" :visible.sync="dialogFormVisible">
            <el-form :model="subForm" :rules="FormRules" ref="subForm">
              <el-form-item
                label="科目名称"
                :label-width="formLabelWidth"
                prop="subName"
              >
                <el-input
                  v-model="subForm.subName"
                  style="width: 250px"
                ></el-input>
              </el-form-item>
              <el-form-item
                label="授课教师"
                :label-width="formLabelWidth"
                prop="teacher.id"
              >
                <el-cascader
                  @change="valueToCascade"
                  v-model="teacherName"
                  placeholder="请选择授课教师"
                  :options="tchArr"
                  filterable
                  :show-all-levels="false"
                  :props="{ expandTrigger: 'hover' }"
                ></el-cascader>
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
              data.subName.toLowerCase().includes(search.toLowerCase()) ||
              data.teacher.tchName.toLowerCase().includes(search.toLowerCase())
          )
        "
        height="480px"
        border
        style="width: 100%"
        @selection-change="handleSelectionChange"
      >
        <el-table-column type="selection" width="40"> </el-table-column>
        <el-table-column type="index" label="序号" width="50">
        </el-table-column>
        <el-table-column prop="subName" label="科目名称"> </el-table-column>
        <el-table-column prop="teacher.tchName" label="授课教师">
        </el-table-column>
        <el-table-column label="搜索">
          <!-- eslint-disable-next-line -->
          <template slot="header" slot-scope="scope">
            <el-input
              v-model="search"
              size="mini"
              placeholder="输入科目或教师姓名进行搜索"
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
            <el-dialog title="修改科目信息" :visible.sync="modifyFormVisible">
              <el-form :model="subForm" :rules="FormRules">
                <el-form-item
                  label="科目名称"
                  :label-width="formLabelWidth"
                  prop="subName"
                >
                  <el-input
                    v-model="subForm.subName"
                    style="width: 250px"
                  ></el-input>
                </el-form-item>
                <el-form-item
                  label="授课教师"
                  :label-width="formLabelWidth"
                  prop="teacher.id"
                >
                  <el-cascader
                    @change="valueToCascade"
                    v-model="teacherName"
                    placeholder="请选择授课教师"
                    :options="tchArr"
                    filterable
                    :show-all-levels="false"
                    :props="{ expandTrigger: 'hover' }"
                  ></el-cascader>
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
      tchArr: [],
      search: "",
      multiSelection: [],
      dialogTableVisible: false,
      modifyFormVisible: false,
      dialogFormVisible: false,
      subForm: {
        subId: "",
        subName: "",
        teacher: {
          id: "",
          tchName: "",
        },
      },
      teacherName: [],
      formLabelWidth: "200px",
      FormRules: {
        subName: [{ required: true, message: "请填写科目", trigger: "blur" }],
        id: [{ required: false, message: "请选择授课教师", trigger: "blur" }],
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
      this.size = size;
      this.findAll();
    },
    handleCurrentChange(pageno) {
      this.pageno = pageno - 1;
      this.findAll();
    },
    valueToCascade(row) {
      this.subForm.teacher.id = row[1];
    },
    clearFormFields() {
      this.subForm = {
        subId: "",
        subName: "",
        teacher: {
          id: "",
          tchName: "",
        },
      };
      this.teacherName = [];
    },

    loadData() {
      this.findAll();
    },
    findAll() {
      this.$ajax
        .post(
          "/sub/findAll",
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

    findAllTch() {
      this.$ajax
        .post(
          "/sub/findAllTch",
          this.$qs.stringify({ tchId: this.subForm.teacher.id }),
          { headers: authHeader() }
        )
        .then((response) => {
          this.tchArr = response.data;
        });
    },
    add() {
      this.$refs["subForm"].validate((valid) => {
        if (!valid) {
          console.log("error submit!!");
          return;
        } else {
          this.$ajax
            .get("/sub/add", {
              headers: authHeader(),
              params: {
                subId: this.subForm.subId,
                subName: this.subForm.subName,
                teacher: this.subForm.teacher.id,
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
              "/sub/del",
              this.$qs.stringify({
                subId: row.subId,
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
      this.multiSelection = val;
    },
    multidel() {
      this.$confirm("此操作将永久删除信息, 是否继续?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(() => {
          let checkArr = this.multiSelection;
          let params = [];
          checkArr.forEach(function (item) {
            params.push(item.subId);
          });
          this.$ajax
            .post(
              "/sub/del",
              this.$qs.stringify(
                {
                  subId: params,
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
        .post("/sub/findById", this.$qs.stringify({ subId: row.subId }), {
          headers: authHeader(),
        })
        .then((res) => {
          let subject = res.data;
          let Form = this.subForm;
          Form.subId = subject.subId;
          Form.subName = subject.subName;
          if (subject.teacher != null) {
            Form.teacher.id = subject.teacher.id;
            Form.teacher.tchName = subject.teacher.tchName;
            this.teacherName = [subject.teacher.major, subject.teacher.id];
          }
          this.findAllTch();
        });
    },
    modify() {
      if (this.subForm.subName == "") {
        this.$message.warning("请填写科目名称");
        return;
      }
      this.$ajax
        .post(
          "/sub/modify",
          this.$qs.stringify(
            {
              subId: this.subForm.subId,
              subName: this.subForm.subName,
              teacher: this.subForm.teacher.id,
            },
            { indices: false }
          ),
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