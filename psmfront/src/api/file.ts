import request from '@/utils/request'

export const queryFile = async (projectId: string, parentId: string | null) => {
    const res = await request.post(`/file/queryFile`, {
        projectId: projectId,
        parentId: parentId
    })
    if (res.success) return res.data
}
export const queryFileByFileId = async (fileId:number) => {
    const res = await request.get(`/file/queryFileByFileId/${fileId}`)
    if (res.success) return res.data
}
export const downloadFile = async (data:any)=>{
    request.download(`/file/downloadFile`,data).then(response => {
        // 创建一个临时的 URL 地址
        const url = window.URL.createObjectURL(response);
        // 创建一个 <a> 标签
        const a = document.createElement('a');
        a.style.display = 'none';
        a.href = url;
        // 设置文件下载的名称
        a.download = data.fileName; // 替换为实际的文件名称
        // 将 <a> 标签添加到页面上
        document.body.appendChild(a);
        // 触发点击事件
        a.click();
        // 释放 URL 对象
        window.URL.revokeObjectURL(url);
        // 移除 <a> 标签
        document.body.removeChild(a);
      })
      .catch(error => {
        console.error('下载文件失败:', error);
        // 下载失败处理
      })
}
