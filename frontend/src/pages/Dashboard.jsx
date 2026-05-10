import Header from '../components/layout/Header'
import ProductGrid from '../components/products/ProductGrid'
import PlaceOrderForm from '../components/orders/PlaceOrderForm'
import OrderResult from '../components/orders/OrderResult'
import OrderHistory from '../components/orders/OrderHistory'
import DomainRulePanel from '../components/dashboard/DomainRulePanel'
import ApiResponseViewer from '../components/dashboard/ApiResponseViewer'
import useProducts from '../hooks/useProducts'
import useOrders from '../hooks/useOrders'

const Dashboard = () => {
  const {
    products,
    productLookup,
    decrementStock,
    restoreStock,
  } = useProducts()

  const {
    isSubmitting,
    lastOrder,
    lastError,
    rawResponse,
    orderHistory,
    submitOrder,
    cancelExistingOrder,
  } = useOrders()

  const handlePlaceOrder = async ({ productId, quantity, customerId, customerType }) => {
    const result = await submitOrder({ productId, quantity, customerId, customerType })
    if (result.success) {
      decrementStock(productId, quantity)
    }
  }

  const handleCancelOrder = async (order) => {
    const cancelledOrder = await cancelExistingOrder(order.orderId)
    const item = cancelledOrder.items?.[0]
    if (!item) {
      return
    }

    const parsedProductId = Number(item.productId)
    restoreStock(Number.isNaN(parsedProductId) ? item.productId : parsedProductId, item.quantity)
  }

  const orderProductName = (order) => {
    const productId = order.items?.[0]?.productId
    if (!productId) {
      return 'Unknown'
    }
    return productLookup[Number(productId)]?.name ?? 'Unknown'
  }

  const normalizedHistory = orderHistory.map((order) => ({
    ...order,
    items: order.items?.map((item) => ({
      ...item,
      productName: item.productName ?? orderProductName(order),
    })),
  }))

  return (
    <div className="min-h-screen bg-slate-900 px-6 py-6 text-white">
      <div className="mx-auto flex max-w-6xl flex-col gap-6">
        <Header />

        <div className="grid gap-6 lg:grid-cols-[1.2fr_1fr]">
          <ProductGrid products={products} />

          <div className="flex flex-col gap-6">
            <PlaceOrderForm
              products={products}
              onSubmit={handlePlaceOrder}
              isSubmitting={isSubmitting}
            />
            <OrderResult order={lastOrder} error={lastError} />
            <DomainRulePanel lastError={lastError} />
          </div>
        </div>

        <OrderHistory orders={normalizedHistory} onCancel={handleCancelOrder} />
        <ApiResponseViewer data={rawResponse} />
      </div>
    </div>
  )
}

export default Dashboard
