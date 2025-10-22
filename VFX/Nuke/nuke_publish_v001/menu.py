import nuke
import nuke_publish  # This loads your plugin script

# Add to Nuke's main menu under "Custom"
toolbar = nuke.menu("Nodes")
custom_menu = toolbar.addMenu("Custom Tools", icon="Backdrop.png")  # You can use any icon
custom_menu.addCommand("Publish Tool", "nuke_publish.publish()")
