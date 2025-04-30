<template>
  <div class="p-8">
    <h1 class="text-2xl font-bold mb-4">📄 Markdown 流式展示</h1>

    <div id="markdown-content" class="prose max-w-none"></div>

    <div v-if="loading" class="mt-4 flex items-center text-gray-500">
      <svg class="animate-spin h-5 w-5 mr-2 text-blue-500" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24">
        <circle class="opacity-25" cx="12" cy="12" r="10" stroke="currentColor" stroke-width="4"></circle>
        <path class="opacity-75" fill="currentColor" d="M4 12a8 8 0 018-8v8H4z"></path>
      </svg>
      加载中...
    </div>

    <div v-if="error" class="mt-4 text-red-500">{{ error }}</div>
  </div>
</template>

<script setup lang="ts">
import { onMounted, ref, nextTick } from 'vue'
import { marked } from 'marked'

const loading = ref(true)
const error = ref('')
const markdownContent = ref('')

onMounted(() => {
  fetch('http://localhost:8080/v4/api/markdownStream')
      .then(response => {
        const reader = response.body?.getReader()
        const decoder = new TextDecoder('utf-8')

        function readChunk() {
          reader?.read().then(({ done, value }) => {
            if (done) {
              loading.value = false
              return
            }
            const chunk = decoder.decode(value)
            const cleaned = chunk.replace(/^data:\s?/gm, '');
            console.log('收到数据块:', chunk)
            markdownContent.value += cleaned
            renderMarkdownAndScroll()
            readChunk()
          })
        }

        readChunk()
      })
      .catch(err => {
        console.error('fetch error:', err)
        error.value = '请求失败: ' + err.message
        loading.value = false
        markdownContent.value += `\n| Error |\n| --- |\n| ${err.message} |\n`
        renderMarkdownAndScroll()
      })
})

function renderMarkdownAndScroll() {
  const container = document.getElementById('markdown-content')
  if (container) {
    container.innerHTML = marked.parse(markdownContent.value)
    nextTick(() => {
      container.scrollTop = container.scrollHeight
    })
  }
}
</script>

<style scoped>
.prose {
  font-family: Arial, sans-serif;
  background-color: #f9fafb;
  padding: 20px;
  border-radius: 8px;
  overflow-y: auto;
  max-height: 80vh;
}
.prose table {
  width: 100%;
  border-collapse: collapse;
}
.prose table, .prose th, .prose td {
  border: 1px solid #ccc;
}
.prose th, .prose td {
  padding: 8px;
  text-align: left;
}
</style>