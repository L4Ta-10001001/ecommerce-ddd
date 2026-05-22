import { useMemo, useState } from 'react'
import { SEED_PRODUCTS } from '../constants'

const buildProductLookup = (items) => Object.fromEntries(
  items.map((product) => [product.id, product])
)

const useProducts = () => {
  const [products, setProducts] = useState(SEED_PRODUCTS)

  const productLookup = useMemo(() => buildProductLookup(products), [products])

  const decrementStock = (productId, quantity) => {
    setProducts((current) => current.map((product) => {
      if (product.id !== productId) {
        return product
      }

      const nextStock = Math.max(0, product.stock - quantity)
      return { ...product, stock: nextStock }
    }))
  }

  const restoreStock = (productId, quantity) => {
    setProducts((current) => current.map((product) => {
      if (product.id !== productId) {
        return product
      }

      const nextStock = Math.min(product.maxStock, product.stock + quantity)
      return { ...product, stock: nextStock }
    }))
  }

  return {
    products,
    productLookup,
    decrementStock,
    restoreStock,
  }
}

export default useProducts
