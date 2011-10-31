package com.github.fng.commonsscalaswing.table

import javax.swing.table.TableCellRenderer
import javax.swing.{UIManager, JTable, JButton}
import java.awt.{Component}
import swing.Button

object CellRenderer {

  class ButtonTableCellRenderer extends JButton with TableCellRenderer {
    setOpaque(true)

    def getTableCellRendererComponent(table: JTable, value: AnyRef, isSelected: Boolean, hasFocus: Boolean, row: Int,
                                      column: Int): Component = {

      if (isSelected) {
        setForeground(table.getSelectionForeground)
        setBackground(table.getSelectionBackground)
      } else {
        setForeground(table.getForeground)
        setBackground(UIManager.getColor("Button.background"))
      }

      value match {
        case button: Button => setText(button.text)
        case null => setText("")
        case other => setText(other.toString)
      }

      this
    }
  }

}