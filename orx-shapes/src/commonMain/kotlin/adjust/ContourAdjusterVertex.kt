package org.openrndr.extra.shapes.adjust

import org.openrndr.extra.shapes.vertex.ContourVertex
import org.openrndr.math.Vector2

class ContourAdjusterVertex(val contourAdjuster: ContourAdjuster, val segmentIndex: () -> Int) {
    private fun wrap(block: ContourVertex.() -> ContourVertex) {
        val newVertex = ContourVertex(contourAdjuster.contour, segmentIndex()).block()
        contourAdjuster.contour = newVertex.contour
        contourAdjuster.updateSelection(newVertex.adjustments)
    }

    val position: Vector2
        get() {
            return contourAdjuster.contour.segments[segmentIndex()].start
        }

    fun select() {
        contourAdjuster.selectVertex(segmentIndex())
    }

    fun remove(updateTangents: Boolean = true) = wrap { remove(updateTangents) }
    fun moveBy(translation: Vector2, updateTangents: Boolean = true) = wrap { movedBy(translation, updateTangents) }
    fun rotate(rotationInDegrees: Double) = wrap { rotatedBy(rotationInDegrees) }
    fun scale(scaleFactor: Double) = wrap { scaledBy(scaleFactor) }

    fun rotate(rotationInDegrees: Double, anchor: Vector2) = wrap { rotatedBy(rotationInDegrees, anchor) }
    fun scale(scaleFactor: Double, anchor: Vector2) = wrap { scaledBy(scaleFactor, anchor) }

}