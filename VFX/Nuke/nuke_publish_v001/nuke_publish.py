import nuke
import os
import re
import json
import subprocess
from datetime import datetime

def _get_shot_name():
    name = nuke.root().name()
    if name and name != "Root":
        base = os.path.basename(name)
        return os.path.splitext(base)[0]
    return "comp"

def _next_version(out_dir, base):
    pattern = re.compile(re.escape(base) + r"_v(\d{3})")
    vnum = []
    if not os.path.exists(out_dir):
        return 1
    for i in os.listdir(out_dir):
        m = pattern.search(i)
        if m:
            vnum.append(int(m.group(1)))
    return max(vnum) + 1 if vnum else 1

def _get_write_node(seq_path):
    selected = [n for n in nuke.selectedNodes() if n.Class() == "Write"]
    if selected:
        w = selected[0]
        w["file"].setValue(seq_path)
        return w
    
    existing = [n for n in nuke.allNodes("Write")]
    if existing:
        existing[0]["file"].setValue(seq_path)
        return existing[0]
    
    last_nodes = [n for n in nuke.allNodes() if not n.dependent()]
    if not last_nodes:
        raise RuntimeError("No node to connect to for rendering.")
    
    w = nuke.nodes.Write(file=seq_path)
    w.setInput(0, last_nodes[0])
    return w

def _render_write(seq_path):
    w = _get_write_node(seq_path)
    first = int(nuke.root()['first_frame'].value())
    last = int(nuke.root()['last_frame'].value())
    nuke.execute(w.name(), first, last)
    return first, last

def _ffmpeg_make_mp4(seq_pattern, out_mp4, framerate=24):
    cmd = [
        "ffmpeg", "-y",
        "-framerate", str(framerate),
        "-i", seq_pattern,
        "-vf", "scale=trunc(iw/2)*2:trunc(ih/2)*2",
        "-c:v", "libx264", "-pix_fmt", "yuv420p",
        out_mp4
    ]
    subprocess.check_call(cmd)

def publish_shot(output_folder=None, shot_name=None, make_mp4=True):
    if not output_folder:
        default = os.path.expanduser("~/nuke_publishes")
        p = nuke.Panel("Publish Shot")
        p.addSingleLineInput("Output folder", default)
        p.addSingleLineInput("Shot Name", _get_shot_name())
        p.addBooleanCheckBox("Generate MP4", True)
        if not p.show():
            return
        output_folder = p.value("Output folder")
        shot_name = p.value("Shot Name")
        make_mp4 = p.value("Generate MP4")

    os.makedirs(output_folder, exist_ok=True)
    version = _next_version(output_folder, shot_name)
    seq_template = os.path.join(output_folder, f"{shot_name}_v{version:03d}.%04d.exr")
    print("Rendering to:", seq_template)

    first, last = _render_write(seq_template)

    mp4_path = None
    if make_mp4:
        mp4_path = os.path.join(output_folder, f"{shot_name}_v{version:03d}.mp4")
        _ffmpeg_make_mp4(seq_template, mp4_path, framerate=int(nuke.root()['fps'].value()))
        print("MP4 created:", mp4_path)

    meta = {
        "shot": shot_name,
        "version": f"v{version:03d}"),
        "author": os.environ.get("USER", "unknown"),
        "rendered_at": f"{datetime.utcnow().isoformat()}Z",
        "frames": {"first": first, "last": last},
        "sequence_pattern": seq_template,
        "mp4": mp4_path
    }

    meta_path = os.path.join(output_folder, f"{shot_name}_v{version:03d}_publish_meta.json")
    with open(meta_path, "w") as f:
        json.dump(meta, f, indent=2)
    print("Published:", meta_path)
    return meta

def publish():
    try:
        publish_shot()
    except Exception as e:
        nuke.message(f"publish failed:\n{e}")

try:
    m = nuke.menu("Nuke")
    m.addCommand("Custom/Publish Shot", "import nuke_publish; nuke_publish.publish_shot()", "ctrl+shift+p")
except Exception as e:
    print("Could not add menu entry:", e)
