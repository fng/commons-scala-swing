package com.github.fng.commonsscalaswing.table

import java.awt.Color
import javax.swing._
import java.awt.event.{ActionEvent, ActionListener}
import java.awt.Component
import swing.Button
import table.TableCellEditor

trait ComponentCellEditor[T] {
  def editor(tableModel: GenericTableModel[T], row: Int, column: Int): TableCellEditor
}

object CellEditor {


  class ComboboxCellEditor[T](values: List[AnyRef]) extends ComponentCellEditor[T] {
    val theEditor = new DefaultCellEditor(new JComboBox(values.toArray))

    def editor(tableModel: GenericTableModel[T], row: Int, column: Int) = theEditor
  }


  class ButtonTableCellEditor[T](clickEventHandler: Int => Unit) extends ComponentCellEditor[T] {
    val theEditor = new DefaultCellEditor(new JCheckBox) {
      val button = new JButton
      button.setOpaque(true)
      button.addActionListener(new ActionListener {
        def actionPerformed(e: ActionEvent) {
          fireEditingStopped
        }
      })

      private var label: String = ""
      private var id: Option[Int] = None
      private var isPushed: Boolean = false

      override def getTableCellEditorComponent(table: JTable, value: AnyRef, isSelected: Boolean, row: Int,
                                               column: Int): Component = {
        if (isSelected) {
          button.setForeground(table.getSelectionForeground)
          button.setBackground(table.getSelectionBackground)
        } else {
          button.setForeground(table.getForeground)
          button.setBackground(table.getBackground)
        }
        label = value match {
          case button: Button => button.text
          case null => ""
          case other => other.toString
        }
        button.setText(label)
        isPushed = true

        id = Option(row)

        button
      }

      override def getCellEditorValue: AnyRef = {
        if (isPushed) {
          id.foreach(id => clickEventHandler(id))
        }
        isPushed = false
        id = None
        label
      }

      override def stopCellEditing: Boolean = {
        isPushed = false
        id = None
        super.stopCellEditing
      }

      override def fireEditingStopped {
        super.fireEditingStopped
      }
    }


    def editor(tableModel: GenericTableModel[T], row: Int, column: Int) = theEditor
  }


}