package itmo.skymap.navigator.thread

import itmo.skymap.navigator.renderer.ImageManager
import itmo.skymap.navigator.renderer.LineManager
import itmo.skymap.navigator.renderer.MainRenderer
import itmo.skymap.navigator.renderer.Renderer
import itmo.skymap.navigator.rendertype.ImageRun
import itmo.skymap.navigator.rendertype.LineRun

abstract class RendererTypeExecutorHelper(protected val renderer: MainRenderer) {

    abstract class AbstractRenderType<E> internal constructor(var manager: Renderer) {

        fun executeMaxFieldOfView(fov: Double, controller: RendererTypeExecutorHelper) {
            controller execute { manager.setMaxRadiusOfView(fov) }
        }

        abstract fun executeObjects(objects: List<E>, controller: RendererTypeExecutorHelper)
    }

    class LineRender internal constructor(manager: LineManager) : AbstractRenderType<LineRun>(manager) {
        override fun executeObjects(objects: List<LineRun>, controller: RendererTypeExecutorHelper) =
            controller execute { (manager as LineManager).updateObjects(objects) }

    }

    class ImageRender internal constructor(manager: ImageManager) : AbstractRenderType<ImageRun>(manager) {
        override fun executeObjects(objects: List<ImageRun>, controller: RendererTypeExecutorHelper) =
            controller execute { (manager as ImageManager).updateObjects(objects) }
    }

    protected interface Runner {
        infix fun run(r: Runnable)
    }

    fun createLineManager(layer: Int): LineRender {
        val manager = LineRender(renderer.createPolyLineManager(layer))
        executeAddManager(manager)
        return manager
    }

    fun createImageManager(layer: Int): ImageRender {
        val manager = ImageRender(renderer.createImageManager(layer))
        executeAddManager(manager)
        return manager
    }

    fun executeView(fov: Double) {
        execute { renderer.setRadiusOfView(fov) }
    }

    fun executeDirections(
        dirX: Double, dirY: Double, dirZ: Double,
        upX: Double, upY: Double, upZ: Double,
    ) {
        execute {
            renderer.run {
                viewOrientation = ViewOrientation(dirX, dirY, dirZ, upX, upY, upZ)
                viewOrientation.execute()
            }
        }
    }

    fun addRender(runnable: Runnable?) {
        execute { runnable?.let { renderer.addUpdateClosure(it) } }
    }

    fun deleteRender(update: Runnable?) {
        execute { update?.let { renderer.removeUpdateCallback(it) } }
    }

    private fun <E> executeAddManager(rom: AbstractRenderType<E>) {
        execute { renderer.addObjectManager(rom.manager) }
    }

    protected abstract val _runner: Runner

    protected infix fun execute(r: Runnable) {
        val runner = _runner
        execute(runner, r)
    }

    companion object {
        private fun execute(runner: Runner, r: Runnable) = runner run (r)
    }
}
