import { useState } from 'react'
import { cancelOrder, placeOrder } from '../api/ordersApi'
import { CUSTOMER_TYPES, DEFAULT_BACKEND_ORDER_ID, DEFAULT_CUSTOMER_ID, LABELS } from '../constants'

const useOrders = () => {
  const [isSubmitting, setIsSubmitting] = useState(false)
  const [lastOrder, setLastOrder] = useState(null)
  const [lastError, setLastError] = useState(null)
  const [rawResponse, setRawResponse] = useState(null)
  const [orderHistory, setOrderHistory] = useState([])
  const [backendOrderIdMap, setBackendOrderIdMap] = useState(new Map())

  const buildOrderPayload = ({ customerId, customerType, productId, quantity }) => ({
    customerId,
    customerType,
    items: [{ productId, quantity }],
  })

  const submitOrder = async ({
    productId,
    quantity,
    customerId = DEFAULT_CUSTOMER_ID,
    customerType = CUSTOMER_TYPES.REGULAR,
  }) => {
    setIsSubmitting(true)
    setLastError(null)

    try {
      const payload = buildOrderPayload({
        productId,
        quantity,
        customerId,
        customerType,
      })

      const response = await placeOrder(payload)
      setBackendOrderIdMap((current) => {
        const next = new Map(current)
        const nextId = next.size + DEFAULT_BACKEND_ORDER_ID
        next.set(response.orderId, nextId)
        return next
      })
      setLastOrder(response)
      setRawResponse(response)
      setOrderHistory((current) => [response, ...current])
      return { success: true, data: response }
    } catch (error) {
      const errorMessage = error?.response?.data?.error || LABELS.errorUnknown
      const errorPayload = { error: errorMessage }
      setLastError(errorPayload)
      setRawResponse(errorPayload)
      return { success: false, error: errorPayload }
    } finally {
      setIsSubmitting(false)
    }
  }

  const cancelExistingOrder = async (orderId) => {
    const backendId = backendOrderIdMap.get(orderId) ?? DEFAULT_BACKEND_ORDER_ID
    const response = await cancelOrder(backendId)
    setRawResponse(response)
    setOrderHistory((current) => current.map((order) => (
      order.orderId === orderId ? { ...response, orderId } : order
    )))
    return response
  }

  return {
    isSubmitting,
    lastOrder,
    lastError,
    rawResponse,
    orderHistory,
    backendOrderIdMap,
    submitOrder,
    cancelExistingOrder,
  }
}

export default useOrders
