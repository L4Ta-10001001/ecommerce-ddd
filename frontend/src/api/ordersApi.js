import axiosClient from './axiosClient'

export const placeOrder = async (payload) => {
  const response = await axiosClient.post('/api/orders', payload)
  return response.data
}

export const cancelOrder = async (orderId) => {
  const response = await axiosClient.delete(`/api/orders/${orderId}`)
  return response.data
}

export const fetchOrder = async (orderId) => {
  const response = await axiosClient.get(`/api/orders/${orderId}`)
  return response.data
}
