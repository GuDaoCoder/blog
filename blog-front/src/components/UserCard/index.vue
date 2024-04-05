<script lang="ts" setup></script>

<template>
  <a-card :bordered="false" hoverable>
    <div class="avatar-wrapper">
      <a-avatar :size="84" class="avatar"
                imageUrl="http://cdn.road4code.com/blog/WechatIMG21.jpg"
      />
    </div>

    <div class="name-wrapper">孤岛</div>

  </a-card>
</template>

<style lang="scss" scoped>
.avatar-wrapper {
  width: 100%;
  text-align: center;

  .avatar {
    transition: 0.5s;

    &:hover {
      transform: scale(1.2);
    }
  }
}

.name-wrapper {
  padding: 10px 0;
  width: 100%;
  text-align: center;
  font-size: 24px;
}
</style>