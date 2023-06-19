package iti.mad.marketly.data.model.draftorderresponse

data class DraftOrder(
    val admin_graphql_api_id: String,
    val applied_discount: Any,
    val billing_address: BillingAddress,
    val completed_at: Any,
    val created_at: String,
    val currency: String,
    val customer: Customer,
    val email: String,
    val id: Int,
    val invoice_sent_at: Any,
    val invoice_url: String,
    val line_items: List<LineItem>,
    val name: String,
    val note: Any,
    val note_attributes: List<Any>,
    val order_id: Any,
    val payment_terms: Any,
    val presentment_currency: String,
    val shipping_address: ShippingAddress,
    val shipping_line: Any,
    val status: String,
    val subtotal_price: String,
    val subtotal_price_set: SubtotalPriceSet,
    val tags: String,
    val tax_exempt: Boolean,
    val tax_lines: List<Any>,
    val taxes_included: Boolean,
    val total_additional_fees_set: Any,
    val total_discounts_set: TotalDiscountsSet,
    val total_duties_set: Any,
    val total_line_items_price_set: TotalLineItemsPriceSet,
    val total_price: String,
    val total_price_set: TotalPriceSet,
    val total_shipping_price_set: TotalShippingPriceSet,
    val total_tax: String,
    val total_tax_set: TotalTaxSet,
    val updated_at: String
)