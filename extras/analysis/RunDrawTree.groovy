import javax.swing.*
import javax.swing.tree.*

// Read draws.txt from current directory or command line arg
def drawsFile = args.length > 0 ? new File(args[0]) : new File('draws.txt')
def lines = []
if (drawsFile.exists()) {
    drawsFile.eachLine { line ->
        if (line.contains('<')) {
            lines << line
        }
    }
}
println "${lines.size()} draw lines loaded from ${drawsFile}"

def root = DrawTree.createTree(lines)

SwingUtilities.invokeLater {
    def frame = new JFrame('NTest Draw Tree')
    frame.defaultCloseOperation = JFrame.EXIT_ON_CLOSE
    def tree = new JTree(root)
    tree.cellRenderer = new DefaultTreeCellRenderer() {
        @Override
        java.awt.Component getTreeCellRendererComponent(JTree t, Object value, boolean sel, boolean expanded, boolean leaf, int row, boolean hasFocus) {
            def comp = super.getTreeCellRendererComponent(t, value, sel, expanded, leaf, row, hasFocus)
            String text = value.toString()
            if (text.startsWith('<html>')) {
                setText(text)
            }
            return comp
        }
    }
    frame.contentPane.add(new JScrollPane(tree))
    frame.setSize(800, 600)
    frame.setLocationRelativeTo(null)
    frame.visible = true
}
