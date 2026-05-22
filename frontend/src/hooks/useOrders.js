import { useState } from 'react'
import { cancelOrder, placeOrder } from '../api/ordersApi'
import { CUSTOMER_TYPES, DEFAULT_CUSTOMER_ID, LABELS } from '../constants'

const buildErrorMessage = (error) => {
  const responseData = error?.response?.data

  if (responseData?.error) {
    return responseData.error
  }

  if (responseData?.message) {
    return responseData.message
  }

  if (typeof responseData === 'string') {
    return responseData
  }

  if (error?.message) {
    return error.message
  }

  return LABELS.errorUnknown
}

const useOrders = () => {
  const [isSubmitting, setIsSubmitting] = useState(false)
  const [lastOrder, setLastOrder] = useState(null)
  const [lastError, setLastError] = useState(null)
  const [rawResponse, setRawResponse] = useState(null)
  const [orderHistory, setOrderHistory] = useState([])

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
      setLastOrder(response)
      setRawResponse(response)
      setOrderHistory((current) => [response, ...current])
      return { success: true, data: response }
    } catch (error) {
      const errorMessage = buildErrorMessage(error)
      const errorPayload = { error: errorMessage }
      const responseData = error?.response?.data
      setLastError(errorPayload)
      setRawResponse(responseData ?? errorPayload)
      return { success: false, error: errorPayload }
    } finally {
      setIsSubmitting(false)
    }
  }

  const cancelExistingOrder = async (orderId) => {
    const response = await cancelOrder(orderId)
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
    submitOrder,
    cancelExistingOrder,
  }
}

export default useOrders
