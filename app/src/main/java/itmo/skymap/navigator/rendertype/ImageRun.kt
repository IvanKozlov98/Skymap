package itmo.skymap.navigator.rendertype

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import com.example.math_module.geometry.Vector3D
import com.example.math_module.geometry.mirror
import com.example.math_module.geometry.mult
import com.example.math_module.geometry.normalized
import itmo.skymap.maths.RaDec
import itmo.skymap.maths.coords.GeocentricCoordinates

open class ImageRun(
    coords: GeocentricCoordinates, protected val resources: Resources?, id: Int, upVec: Vector3D,
    private val imageScale: Double,
) {
    val location: GeocentricCoordinates = coords
    var image: Bitmap? = null
    val verticalCorner: DoubleArray
        get() = doubleArrayOf(viewVector.x, viewVector.y, viewVector.z)
    val horizontalCorner: DoubleArray
        get() = doubleArrayOf(upVector.x, upVector.y, upVector.z)

    private var upVector: Vector3D = Vector3D(0.0, 0.0, 0.0)
    private var viewVector: Vector3D = Vector3D(0.0, 0.0, 0.0)

    init {
        setUpVector(upVec)
        setImageId(id)
    }

    fun setUpVector(upVec: Vector3D) {
        val perpendicular: Vector3D = location
        val multV = perpendicular.mult(upVec)
        val up = mirror(multV.normalized())
        val dir = up.mult(perpendicular)

        upVector = up.getScaledVector()
        viewVector = dir.getScaledVector()
    }

    private fun setImageId(imageId: Int) {
        val options = BitmapFactory.Options()
        options.inScaled = false
        image = BitmapFactory.decodeResource(resources, imageId, options)
    }

    private fun Vector3D.getScaledVector(): Vector3D {
        this.scale(imageScale)
        return Vector3D(this.x, this.y, this.z)
    }
}

class LineRun @JvmOverloads constructor(
    _color: Int = Color.WHITE,
    val lineWidth: Double = 1.5,
)  {
    val raDecs: ArrayList<RaDec> = ArrayList()
    val color: Int = _color
    val vertices: ArrayList<GeocentricCoordinates> = arrayListOf()
}
