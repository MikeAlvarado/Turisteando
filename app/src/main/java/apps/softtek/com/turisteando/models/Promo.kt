package apps.softtek.com.turisteando.models

data class Promo(
        val PromoTitulo : String,
        val PromoDescripcion : String,
        val PromoParent : String
) {
    constructor() : this("", "", "")
}