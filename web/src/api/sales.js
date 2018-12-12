import request from '@/utils/request'

export function getList(params) {
  return request({
    url: '/admin/getSalesUsers',
    method: 'get',
    params
  })
}

export function saveSales(salesInfo) {
  return request({
    url: '/admin/addSalesUser',
    method: 'post',
    data:salesInfo
  })
}

export function delSales(id) {
  return request({
    url: '/admin/delSalesUser',
    method: 'post',
    data:{id}
  })
}
