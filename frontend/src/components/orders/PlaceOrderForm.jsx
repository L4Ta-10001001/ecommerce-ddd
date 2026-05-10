import { useMemo, useState } from 'react'
import { CLIENT_ACCOUNTS, CUSTOMER_TYPES, DEFAULT_CUSTOMER_ID, LABELS } from '../../constants'

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

  const selectCustomer = (account) => {
    setCustomerId(account.id)
    setCustomerType(account.type)
  }

  return (
    <section className="rounded-2xl border border-slate-700 bg-slate-800/60 p-5 shadow-lg">
      <h2 className="text-xl font-semibold text-white">{LABELS.orderFormTitle}</h2>
      <form className="mt-4 space-y-4" onSubmit={submitForm}>
        <div className="space-y-3">
          <p className="text-sm font-semibold text-slate-200">{LABELS.orderFormCustomerSelect}</p>
          <div className="grid gap-3 sm:grid-cols-2">
            {CLIENT_ACCOUNTS.map((account) => {
              const isSelected = account.id === customerId
              return (
                <button
                  type="button"
                  key={account.id}
                  onClick={() => selectCustomer(account)}
                  className={`rounded-2xl border px-4 py-3 text-left transition ${isSelected
                    ? 'border-indigo-400 bg-indigo-500/20'
                    : 'border-slate-700 bg-slate-900/60 hover:border-indigo-500/60'}
                  `}
                >
                  <div className="flex items-center justify-between">
                    <p className="text-sm font-semibold text-white">{account.name}</p>
                    <span className="text-xs uppercase tracking-wider text-slate-300">
                      {account.type === CUSTOMER_TYPES.REGULAR
                        ? LABELS.orderFormCustomerRegular
                        : LABELS.orderFormCustomerVip}
                    </span>
                  </div>
                  <p className="mt-2 text-xs text-slate-400">{account.id}</p>
                  {isSelected && (
                    <p className="mt-2 text-xs font-semibold text-indigo-200">
                      {LABELS.orderFormCustomerSelected}
                    </p>
                  )}
                </button>
              )
            })}
          </div>
        </div>
        <label className="flex flex-col gap-2 text-sm text-slate-300">
          {LABELS.orderFormProduct}
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
          {LABELS.orderFormQuantity}
          <input
            type="number"
            min={1}
            className="rounded-xl border border-slate-600 bg-slate-900 px-3 py-2 text-white focus:outline-none focus:ring-2 focus:ring-indigo-500"
            value={quantity}
            onChange={(event) => setQuantity(event.target.value)}
          />
        </label>

        <div className="rounded-2xl border border-slate-700 bg-slate-900/60 px-4 py-3 text-sm text-slate-300">
          <p className="text-xs uppercase tracking-widest text-slate-500">{LABELS.orderFormCustomerId}</p>
          <p className="mt-1 text-sm font-semibold text-white">{customerId}</p>
          <p className="mt-2 text-xs uppercase tracking-widest text-slate-500">{LABELS.orderFormCustomerType}</p>
          <p className="mt-1 text-sm font-semibold text-white">{customerType}</p>
        </div>

        <button
          type="submit"
          disabled={isSubmitting}
          className="flex w-full items-center justify-center gap-2 rounded-xl bg-indigo-500 px-4 py-3 text-sm font-semibold text-white transition hover:bg-indigo-400 disabled:cursor-not-allowed disabled:opacity-70"
        >
          {isSubmitting ? LABELS.orderSubmitting : LABELS.orderSubmit}
        </button>
      </form>
    </section>
  )
}

export default PlaceOrderForm
