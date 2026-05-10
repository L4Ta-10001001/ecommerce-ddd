export const API_BASE_URL = 'http://localhost:8081'

export const AUTH_HEADERS = {
  Authorization: 'Basic dXNlcjp1c2VyMTIz',
}

export const DEFAULT_CUSTOMER_ID = '550e8400-e29b-41d4-a716-446655440000'

export const CUSTOMER_TYPES = {
  REGULAR: 'REGULAR',
  VIP: 'VIP',
}

export const ORDER_STATUS = {
  PENDING: 'PENDING',
  CONFIRMED: 'CONFIRMED',
  CANCELLED: 'CANCELLED',
}

export const LABELS = {
  appTitle: 'ecommerce-ddd',
  architectureBadge: 'DDD + Hexagonal Architecture',
  backendLabel: 'backend :8081',
  productsTitle: 'Products & Stock',
  orderHistoryTitle: 'Order History',
  orderFormTitle: 'Place Order',
  orderFormProduct: 'Product',
  orderFormQuantity: 'Quantity',
  orderFormCustomerId: 'Customer ID',
  orderFormCustomerType: 'Customer Type',
  orderSubmit: 'Submit Order',
  orderSubmitting: 'Submitting...',
  orderConfirmedLabel: 'Order confirmed',
  orderResultEmpty: 'Submit an order to see the domain response.',
  orderHistoryEmpty: 'No orders yet. Submit one to see it here.',
  orderHistorySession: 'session only',
  orderIdLabel: 'Order ID',
  orderItemLabel: 'Item',
  orderTotalLabel: 'Total',
  orderCancel: 'Cancel',
  orderStockLabel: 'stock',
  orderUnitsAvailable: 'units available',
  domainRuleSuccess: '✅ Order.addItem() — Stock validated and decremented inside the Aggregate',
  domainRuleError: '⚠️ InsufficientStockException — Rule enforced by Order.addItem(), not by a Service class',
  domainViolation: 'Domain Rule Violated:',
  apiResponseTitle: 'Raw API Response',
  apiResponseEmpty: 'No response yet.',
  apiResponseHide: 'Hide',
  apiResponseShow: 'Show',
  backendStatusUnknown: 'Unknown',
  errorUnknown: 'Unknown error',
}

export const SEED_PRODUCTS = [
  // seed data — loaded by DataSeeder.java on backend startup
  { id: 1, name: 'Laptop', price: 1200.0, stock: 10, maxStock: 10 },
  { id: 2, name: 'Mouse', price: 25.0, stock: 50, maxStock: 50 },
  { id: 3, name: 'Keyboard', price: 75.0, stock: 30, maxStock: 30 },
  { id: 4, name: 'Monitor', price: 350.0, stock: 5, maxStock: 5 },
]
