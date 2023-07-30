<template>
  <div>
    <div style="margin: 0.83em 0">
      <el-button @click="toSubjectInfo()">返回列表</el-button>
      <h2 style="display: inline; margin-left: 20px">
        {{ subjectForm.subjectName }}的知识点框架
      </h2>
      <el-button type="primary" style="float: right" @click="addNewParent()"
        >新建第 {{ nextChapter }} 章</el-button
      >
    </div>
    <el-scrollbar :height="mainHeight">
      <el-tree
        :data="treeData"
        node-key="chapterId"
        default-expand-all
        #default="scope"
        :expand-on-click-node="false"
      >
        <span class="custom-tree-node">
          <span>
            {{ scope.node.label }}
            <el-tag type="danger" v-if="scope.data.isKnowledge">知识点</el-tag>
          </span>

          <span>
            <el-button
              v-if="!scope.data.isKnowledge"
              type="success"
              icon="circle-plus-filled"
              circle
              @click="() => append(scope.data)"
            >
            </el-button>
            <el-button
              type="primary"
              icon="edit"
              circle
              @click="
                clearFormFields();
                dialogFormVisible = true;
                loadInfo(scope.data);
              "
            ></el-button>
            <el-button
              type="danger"
              icon="delete-filled"
              circle
              @click="del(scope.node, scope.data)"
            >
            </el-button>
          </span>
        </span>
      </el-tree>
      <el-dialog title="修改节点信息" v-model="dialogFormVisible" width="600px">
        <el-form
          :model="chapterForm"
          :rules="formRules"
          ref="chapterForm"
          label-width="200px"
          label-position="right"
        >
          <el-form-item label="节点名称" prop="content">
            <el-input
              style="width: 250px"
              v-model="chapterForm.content"
            ></el-input>
          </el-form-item>
          <!-- <el-form-item label="是否是知识点" prop="isKnowledge">
            <el-button-group>
              <el-button
                icon="check"
                round
              ></el-button>
              <el-button
                icon="close"
                round
              ></el-button>
            </el-button-group>
          </el-form-item> -->
        </el-form>
        <template #footer>
          <span class="dialog-footer">
            <el-button @click="dialogFormVisible = false">取 消</el-button>
            <el-button type="primary" @click="update()">确 定</el-button>
          </span>
        </template>
      </el-dialog>
    </el-scrollbar>
  </div>
</template>
<script>
import userToken from "@/services/auth-header";
import { dealSelect } from "@/services/response";
export default {
  data() {
    return {
      subjectForm: {
        subjectId: "",
        subjectName: "",
      },
      mainHeight: "",

      dialogFormVisible: false,
      chapterForm: {
        chapterId: "",
        chapterName: "",
        content: "",
      },
      formRules: {
        content: [
          { required: true, message: "请填写节点信息", trigger: "blur" },
        ],
      },

      newChapterId: 1000,
      newSectionId: 10000,
      nextChapter: 0,
      treeFlatData: [],
      treeData: [],
    };
  },
  created() {
    // windowHeight - headerHeight - header.margin (20px * 2) - title.height - title.margin (0.83em * 2)
    this.mainHeight =
      document.documentElement.clientHeight -
      this.$storage.getStorageSync("headerHeight") -
      40 -
      37 -
      13.28 * 2 +
      "px";
    this.subjectForm.subjectId = this.$route.params.subjectId;
    this.loadSubjectInfo(this.$route.params.subjectId);
    this.loadTreeDataBySubjectId(this.$route.params.subjectId).then(
      (response) => {
        this.nextChapter = this.getNextChapter();
      }
    );
  },
  methods: {
    toSubjectInfo() {
      // data存储在this.treeFlatData中
      this.treeFlatData = [];
      this.getTreeFlatData(this.treeData);
      this.save(this.treeFlatData);
      // 修改成功才跳转，地址放到save()函数的success()中
      // this.$router.push("/teacher/subjectInfo");
    },
    getNextChapter() {
      let label = this.treeData[this.treeData.length - 1].label;
      return parseInt(label.replace(/[^0-9]/gi, "")) + 1;
    },
    getTreeFlatData(data) {
      data.forEach((elem) => {
        this.treeFlatData.push(elem);
        elem.children && elem.children.length > 0
          ? this.getTreeFlatData(elem.children)
          : "";
      });
    },
    clearFormFields() {
      this.chapterForm = {};
      this.$nextTick(() => {
        this.$refs.chapterForm.clearValidate();
      });
    },

    loadSubjectInfo(id) {
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
    loadTreeDataBySubjectId(id) {
      return this.$axios
        .get("/knowledgeFrame/loadKnowledgeFrameBySubjectId", {
          headers: { Authorization: userToken() },
          params: { subjectId: id },
        })
        .then((response) => {
          this.treeData = dealSelect(response.data);
        });
    },

    // 新建
    addNewParent() {
      this.treeData.push({
        chapterId: this.newChapterId++,
        chapterParentId: 0,
        label: "第" + this.getNextChapter() + "章 未填写节点信息",
        isKnowledge: false,
        children: [],
      });
      this.nextChapter = this.getNextChapter();
    },
    append(data) {
      // let vm = this;
      let order = "";
      let isKnowledgeChildren = "";
      if (!data.children.length) {
        // 如果data中没有children，则需要用set来创建
        // vm.$set(data, "children", []);
        let label = data.label.split(" ")[0];
        if (label.split("-").length == 2) {
          // 第1章 -> 1-1 -> 知识点
          isKnowledgeChildren = true;
        } else {
          order += label.match(/[0-9]+(-[0-9]+)?/g) + "-1 ";
        }
      } else {
        isKnowledgeChildren =
          data.children[data.children.length - 1].isKnowledge;
        if (isKnowledgeChildren) {
          order = "";
        } else {
          let label =
            data.children[data.children.length - 1].label.split(" ")[0];
          let section = label.split("-");
          let num = section[section.length - 1];
          order +=
            label.slice(0, label.length - num.length) +
            (parseInt(num) + 1) +
            " ";
        }
      }
      const newChild = {
        chapterId: this.newSectionId++,
        chapterParentId: data.chapterId,
        label: order + "未填写节点信息",
        isKnowledge: isKnowledgeChildren,
        children: [],
      };
      data.children.push(newChild);
    },

    // 编辑
    loadInfo(data) {
      this.chapterForm = {
        chapterId: data.chapterId,
        chapterName: data.isKnowledge ? "" : data.label.split(" ")[0],
        content: data.isKnowledge ? data.label : data.label.split(" ")[1],
      };
    },
    update() {
      this.$refs.chapterForm.validate((valid) => {
        if (valid) {
          this.treeFlatData = [];
          this.getTreeFlatData(this.treeData);
          this.treeFlatData.forEach((elem) => {
            if (elem.chapterId == this.chapterForm.chapterId) {
              // if (elem.children.length && this.chapterForm.isKnowledge) {
              //   this.$message.warning(
              //     "该节点下存在子节点或知识点，无法更改为知识点"
              //   );
              // } else {
              elem.label =
                this.chapterForm.chapterName + " " + this.chapterForm.content;
              // elem.isKnowledge = this.chapterForm.isKnowledge;
              // }
            }
          });
          this.dialogFormVisible = false;
          this.$message({
            type: "success",
            message: "修改成功",
          });
        }
      });
    },

    // 删除
    del(node, data) {
      const parent = node.parent;
      const children = parent.data.children || parent.data;
      const index = children.findIndex((d) => d.chapterId === data.chapterId);
      this.$confirm("此操作将永久删除信息, 是否继续？", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(() => {
          children.splice(index, 1);
          if (!data.isKnowledge && index < children.length) {
            // x-x且非队尾元素 -> 需要调整章节顺序
            for (let i = index; i < children.length; i++) {
              let label = children[i].label.split(" ");
              let name = label[0].split("-");
              children[i].label =
                name[0] + "-" + (parseInt(name[1]) - 1) + " " + label[1];
            }
          }
          this.nextChapter = this.getNextChapter();
        })
        .catch(() => {
          this.$message.info("已取消删除");
        });
    },

    save(treeList) {
      let list = [];
      treeList.forEach((item) => {
        list.push(
          item.chapterId +
            "," +
            item.chapterParentId +
            "," +
            item.label +
            "," +
            item.isKnowledge +
            "," +
            this.subjectForm.subjectId
        );
      });
      this.$axios
        .post(
          "/knowledgeFrame/save",
          this.$qs.stringify(
            {
              knowledgeFrames: list,
              subjectId: this.subjectForm.subjectId,
            },
            { indices: false }
          ),
          {
            headers: { Authorization: userToken() },
          }
        )
        .then((response) => {
          this.$message({
            type: response.data.success ? "success" : "error",
            message: response.data.message,
          });
          if (response.data.success) {
            this.$router.push("/teacher/subjectInfo");
          }
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
.el-tree {
  margin: 0 20px;
}
.custom-tree-node {
  flex: 1;
  display: flex;
  align-items: center;
  justify-content: space-between;
  font-size: 17px;
}
.el-tree-node__content {
  height: 43px;
}
</style>
