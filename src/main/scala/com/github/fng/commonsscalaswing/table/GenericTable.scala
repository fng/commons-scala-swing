package com.github.fng.commonsscalaswing.table

import swing.{Component, Table}
import swing.Table.ElementMode
import javax.swing.table.DefaultTableCellRenderer
import javax.swing.JComponent
import com.github.fng.commonsscalaswing.table.GenericTableModel.Column

abstract class GenericTable[T](columns: List[Column[T, _]], data: List[T]) extends Table {

  val tableModel: GenericTableModel[T] = new GenericTableModel[T](columns, data.toBuffer)
  model = tableModel

  autoResizeMode = Table.AutoResizeMode.AllColumns
  selection.elementMode = ElementMode.Cell

  override protected def editor(row: Int, column: Int) = columns(column).customCellEditor match {
    case Some(editor) => editor.editor(tableModel, row, column)
    case None => super.editor(row, column)
  }

  override protected def rendererComponent(isSelected: Boolean, focused: Boolean, row: Int, column: Int): Component = columns(column).customCellRenderer match {
    case Some(renderer) => renderer.rendererComponent(tableModel, isSelected, focused, row, column,
      defaultRendererComponent = () => {
        new Component {
          override lazy val peer = new DefaultTableCellRenderer().getTableCellRendererComponent(GenericTable.this.peer, tableModel.getValueAt(row, column).asInstanceOf[AnyRef],
            isSelected, focused, row, column).asInstanceOf[JComponent]
        }
      })
    case None => super.rendererComponent(isSelected, focused, row, column)
  }

  columns.map(_.preferredWidth).zipWithIndex.foreach {
    case (preferredWith, index) => preferredWith.foreach {
      width => peer.getColumnModel.getColumn(index).setPreferredWidth(width)
    }
  }

  columns.foreach {
    column => listenTo(column.columnEventPublisher)
  }

}