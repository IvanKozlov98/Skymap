package itmo.skymap.navigator.managers

import itmo.skymap.navigator.pointing.VectorPointing

interface AbstractManager {
    var pointing: VectorPointing?
    fun start()
    fun stop()
}
