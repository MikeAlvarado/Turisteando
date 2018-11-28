package apps.softtek.com.turisteando.models

data class Place(
        val PlaceName : String,
        val PlaceDescription : String,
        val PlacePhoto : String,
        val PlaceParent : String,
        val PlaceAgenda : String

){
    constructor() : this("", "", "", "", "")
}