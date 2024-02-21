package com.powilliam.scalingoctowaffle.data.entities

data class Seller(
    val host: String,
    val inDocumentPriceReference: String,
    val replaceAll: Map<String, String> = mapOf(),
    val substringStartsAt: Long = 0
) {
    companion object {
        fun forTesting(host: String) = Seller(
            host = host,
            inDocumentPriceReference = ".price"
        )
    }
}
