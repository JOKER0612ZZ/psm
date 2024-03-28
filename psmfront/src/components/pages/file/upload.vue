<template>
    <div>
      <el-upload
        class="upload-demo"
        action="//jsonplaceholder.typicode.com/posts/"
        :on-preview="handlePreview"
        :on-remove="handleRemove"
        :before-remove="beforeRemove"
        :auto-upload="false"
        :file-list="fileList"
      >
        <el-button type="primary">点击上传</el-button>
      </el-upload>
    </div>
  </template>
  
  <script setup lang="ts">
  import { ref } from 'vue';
  
  const fileList = ref([]);
  
  const handlePreview = (file: any) => {
    console.log(file);
  };
  
  const handleRemove = (file: any, fileList: any[]) => {
    console.log(file, fileList);
  };
  
  const beforeRemove = (file: any, _fileList: any[]) => {
    return window.confirm(`确定移除 ${file.name}？`);
  };
  </script>
  
  <style scoped>
  .upload-demo {
    display: flex;
  }
  </style>
  