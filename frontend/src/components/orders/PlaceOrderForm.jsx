import { useMemo, useState } from 'react'
import { CUSTOMER_TYPES, LABELS } from '../../constants'

const DEFAULT_CUSTOMER_ID = '550e8400-e29b-41d4-a716-446655440000'

const PlaceOrderForm = ({ products, onSubmit, isSubmitting }) => {
  const [productId, setProductId] = useState(products[0]?.id ?? 1)
  const [quantity, setQuantity] = useState(1)
  const [customerId, setCustomerId] = useState(DEFAULT_CUSTOMER_ID)
  const [customerType, setCustomerType] = useState(CUSTOMER_TYPES.REGULAR)

  const productOptions = useMemo(
    () => products.map((product) => ({
      value: product.id,
      label: `${product.name} ($${product.price.toFixed(2)})`,
    })),
    [products],
  )

  const submitForm = (event) => {
    event.preventDefault()
    onSubmit({
      productId: Number(productId),
      quantity: Number(quantity),
      customerId,
      customerType,
    })
  }

  return (
    <section className="rounded-2xl border border-slate-700 bg-slate-800/60 p-5 shadow-lg">
      <h2 className="text-xl font-semibold text-white">{LABELS.orderFormTitle}</h2>
      <form className="mt-4 space-y-4" onSubmit={submitForm}>
        <label className="flex flex-col gap-2 text-sm text-slate-300">
          Product
          <select
            className="rounded-xl border border-slate-600 bg-slate-900 px-3 py-2 text-white focus:outline-none focus:ring-2 focus:ring-indigo-500"
            value={productId}
            onChange={(event) => setProductId(event.target.value)}
          >
            {productOptions.map((option) => (
              <option key={option.value} value={option.value}>
                {option.label}
              </option>
            ))}
          </select>
        </label>

        <label className="flex flex-col gap-2 text-sm text-slate-300">
          Quantity
          <input
            type="number"
            min={1}
            className="rounded-xl border border-slate-600 bg-slate-900 px-3 py-2 text-white focus:outline-none focus:ring-2 focus:ring-indigo-500"
            value={quantity}
            onChange={(event) => setQuantity(event.target.value)}
          />
        </label>

        <label className="flex flex-col gap-2 text-sm text-slate-300">
          Customer ID
          <input
            type="text"
            className="rounded-xl border border-slate-600 bg-slate-900 px-3 py-2 text-white focus:outline-none focus:ring-2 focus:ring-indigo-500"
            value={customerId}
            onChange={(event) => setCustomerId(event.target.value)}
          />
        </label>

        <label className="flex flex-col gap-2 text-sm text-slate-300">
          Customer Type
          <select
            className="rounded-xl border border-slate-600 bg-slate-900 px-3 py-2 text-white focus:outline-none focus:ring-2 focus:ring-indigo-500"
            value={customerType}
            onChange={(event) => setCustomerType(event.target.value)}
          >
            <option value={CUSTOMER_TYPES.REGULAR}>REGULAR</option>
            <option value={CUSTOMER_TYPES.VIP}>VIP</option>
          </select>
        </label>

        <button
          type="submit"
          disabled={isSubmitting}
          className="flex w-full items-center justify-center gap-2 rounded-xl bg-indigo-500 px-4 py-3 text-sm font-semibold text-white transition hover:bg-indigo-400 disabled:cursor-not-allowed disabled:opacity-70"
        >
          {isSubmitting ? 'Submitting...' : 'Submit Order'}
        </button>
      </form>
    </section>
  )
}

export default PlaceOrderForm
