<template>
  <div>
    <nav
      class="navbar navbar-expand-lg navbar-absolute"
      :class="{ 'bg-white': showMenu, 'navbar-transparent': !showMenu }"
    >
      <div class="container-fluid">
        <div class="navbar-wrapper">
          <a class="navbar-brand" href="#pablo">{{ routeName }}</a>
        </div>

        <collapse-transition>
          <div class="collapse navbar-collapse show" v-show="showMenu">
            <ul class="navbar-nav" :class="$rtl.isRTL ? 'mr-auto' : 'ml-auto'">
              <base-dropdown
                tag="li"
                title-tag="a"
                class="nav-item"
                menu-classes="dropdown-navbar"
                style="cursor: pointer"
              >
                <a
                  slot="title"
                  href="#"
                  data-toggle="dropdown"
                  aria-expanded="true"
                >
                  <div class="photo">
                    <img src="img/default-avatar.png" />
                  </div>
                </a>
                <li class="nav-link">
                  <!-- 修改个人信息 -->
                  <a
                    @click="dialogFormVisible = true"
                    class="nav-item dropdown-item"
                    >Settings</a
                  >
                </li>
                <div class="dropdown-divider"></div>
                <li class="nav-link">
                  <a href @click.prevent="logOut" class="nav-item dropdown-item"
                    >Log out</a
                  >
                </li>
              </base-dropdown>
            </ul>
          </div>
        </collapse-transition>
      </div>
    </nav>
    <el-dialog title="修改个人信息" :visible.sync="dialogFormVisible">
      <el-form :model="form" :rules="FormRules" ref="form">
        <el-form-item label="账号" :label-width="formLabelWidth" prop="account">
          <el-input
            v-model="form.account"
            style="width: 250px"
            readonly="readonly"
          ></el-input>
        </el-form-item>
        <el-form-item
          label="姓名"
          :label-width="formLabelWidth"
          prop="username"
        >
          <el-input v-model="form.username" style="width: 250px"></el-input>
        </el-form-item>
        <el-form-item
          label="密码"
          :label-width="formLabelWidth"
          prop="password"
        >
          <el-input
            type="password"
            v-model="form.password"
            style="width: 250px"
          ></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="updateInfo()">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>
<script>
import { CollapseTransition } from "vue2-transitions";
import authHeader from "@/services/auth-header";
export default {
  components: {
    CollapseTransition,
  },
  computed: {
    routeName() {
      const { name } = this.$route;
      return name;
    },
    isRTL() {
      return this.$rtl.isRTL;
    },
  },
  data() {
    return {
      activeNotifications: false,
      showMenu: false,
      dialogFormVisible: false,
      formLabelWidth: "200px",
      pswd: "",
      form: {
        role: "",
        account: "",
        username: "",
        password: "",
      },
      FormRules: {
        account: [{ required: true, message: "请填写学号", trigger: "blur" }],
        username: [{ required: true, message: "请填写姓名", trigger: "blur" }],
        password: [{ required: true, message: "请填写密码", trigger: "blur" }],
      },
    };
  },
  created() {
    let user = this.$store.state.auth.user;
    this.form.account = user.account;
    this.form.role = user.roles[0];
    this.findInfoByAccount();
  },
  methods: {
    findInfoByAccount() {
      switch (this.form.role) {
        case "ROLE_MANAGER":
          this.form.username = this.form.account;
          this.getInfo(3);
          break;
        case "ROLE_TEACHER":
          this.getInfo(2);
          break;
        case "ROLE_STUDENT":
          this.getInfo(1);
          break;
      }
    },
    getInfo(role) {
      this.$ajax
        .get("/sys/findInfo", {
          headers: authHeader(),
          params: {
            role: role,
            account: this.form.account,
          },
        })
        .then((res) => {
          this.pswd = res.data.password;
          switch (role) {
            case 3:
              this.form.password = "$2a$10$";
              break;
            case 2:
              this.form.username = res.data.tchName;
              this.form.password = "$2a$10$";
              break;
            case 1:
              this.form.username = res.data.stuName;
              this.form.password = "$2a$10$";
              break;
          }
        });
    },
    updateInfo() {
      if (this.form.username == "") {
        this.$message.warning("请填写姓名");
        return;
      }
      if (this.form.password == "") {
        this.$message.warning("请填写密码");
        return;
      }
      if (this.form.password == "$2a$10$") {
        this.form.password = this.pswd;
      }
      this.$ajax
        .post(
          "/sys/updateInfo",
          {
            role: this.form.role,
            account: this.form.account,
            username: this.form.username,
            password: this.form.password,
          },
          { headers: authHeader() }
        )
        .then(
          (res) => {
            if (res.data.message == "OK") {
              this.$message.success("修改成功");
            } else {
              this.$message.error("修改失败");
            }
            this.dialogFormVisible = false;
          },
          (error) => {
            console.log("error:" + error.message);
            this.$message.info("数据出错");
          }
        )
        .catch(function (error) {
          this.$message.info("数据出错");
          console.log(error);
        });
    },

    logOut() {
      this.$store.dispatch("auth/logout");
      this.$router.push("/");
    },
  },
};
</script>
<style>
</style>
