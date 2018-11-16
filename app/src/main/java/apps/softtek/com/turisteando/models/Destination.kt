package apps.softtek.com.turisteando.models

data class Destination(
        val DestinationName : String,
        val DestinationDescription : String,
        val DestinationPhoto : String,
        val DestinationId : String
) {
    constructor() : this("", "", "", "")
}