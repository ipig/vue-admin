import request from '@/utils/request'

export function getIndexData() {
  return request({
    url: '/admin/getIndexData',
    method: 'post',
    data: {}
  })
}

export function saveIndexData(params) {
  if(params.id){
    return request({
      url: '/admin/modifyIndexData',
      method: 'post',
      data: {"id":params.id,"title":params.title,"content":params.content}
    })
  }else{
    return request({
      url: '/admin/addIndexData',
      method: 'post',
      data: {"title":params.title,"content":params.content}
    })
  }
}


export function getProductData(productType) {
  return request({
    url: '/admin/getProductData',
    method: 'post',
    data: {"productType":productType}
  })
}

export function saveProductData(params) {
  if(params.id){ 
    return request({
      url: '/admin/modifyProductData',
      method: 'post',
      data: {"id":params.id,"title":params.title,"content":params.content}
    })
  }else{
    return request({
      url: '/admin/addProductData',
      method: 'post',
      data: {"productType":params.productType,"title":params.title,"content":params.content}
    })
  }
}

export function getFaqData(productType) {
  return request({
    url: '/admin/getFaqData',
    method: 'post',
    data: {"productType":productType}
  })
}

export function saveFaqData(params) {
  return request({
    url: '/admin/modifyFaqData',
    method: 'post',
    data: {"productType":params.productType,"title":params.title,"content":params.content}
  })
}


export function doPost(url,params) {
  return request({
    url: url,
    method: 'post',
    data: params
  })
}


export function getAppUsers() {
  return request({
    url: '/appUser/getAppUsers',
    method: 'post',
    data: {}
  })
}

export function getOldChatLog(userId,start) {
  return request({
    url: '/appUser/getOldChatLog',
    method: 'post',
    data: {"userId":userId,"start":start}
  })
}

export function getNewChatLog(userId,start) {
  return request({
    url: '/appUser/getNewChatLog',
    method: 'post',
    data: {"userId":userId,"start":start}
  })
}

export function addChatLog(userId,content) {
    return request({
      url: '/appUser/addChatLog',
      method: 'post',
      data: {"userId":userId,"content":content}
    })
}

