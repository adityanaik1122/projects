# nuke\_publish — One‑click Shot Publisher for Nuke

**Short description**

`nuke_publish` is a small, production-minded Nuke Python tool that automates a common compositor task: create a versioned EXR sequence from your comp, render it, generate a review MP4, and write a small JSON metadata file for pipeline use. The goal: fast visual payoff for demos and a clean foundation you can grow into studio-grade tooling.

---

## Key features

- One-click publish panel (choose output folder, shot name, generate MP4)
- Automatic versioning (`shotName_v001`, `v002`, ...) based on existing files in the output folder
- Creates EXR image sequence using a `Write` node and `nuke.execute()`
- Optional MP4 review generation via `ffmpeg`
- Creates `*_publish_meta.json` with author, frames, paths, and timestamp
- Adds a menu command to Nuke for quick access

---

## Requirements

- Nuke (any reasonably recent version that supports Python scripting)
- `ffmpeg` installed and available on your system PATH (if you want MP4 generation)
- Place files in your Nuke plugin path (`~/.nuke` by default) or add a custom plugin folder to `NUKE_PATH`

---

## What the tool does (quick overview)

When you run the tool it will:

1. Ask where to save the publish (panel UI) and the shot name.
2. Inspect the chosen folder for existing versions and pick the next `v###` number.
3. Create a `Write` node pointing to a versioned filename pattern and execute the render.
4. Optionally call `ffmpeg` to turn the image sequence into an MP4 review file.
5. Save a JSON metadata file next to the outputs with useful fields that other tools (Houdini, web dashboards) can read.

---

## Installation

1. Copy `nuke_publish.py` into your `~/.nuke` folder (or a folder in `NUKE_PATH`).
2. Create or edit `menu.py` in the same folder and add a menu entry to call the tool. Example line (add to `menu.py`):

```python
import nuke_publish
nuke.menu('Nuke').addCommand('Custom/Publish Shot', 'nuke_publish.publish()', 'ctrl+shift+p')
```

3. Restart Nuke. The menu item appears under the main menu `Custom -> Publish Shot` (or the path you used).

*Note:* If you prefer loading without `menu.py`, you can import the module from Nuke’s Script Editor: `import nuke_publish` and then run `nuke_publish.publish()` manually.

---

## Usage (interactive)

1. Open your comp in Nuke (with frame range and fps set in the Root node).
2. Choose `Custom -> Publish Shot` (or press `Ctrl+Shift+P`).
3. In the panel that appears:
   - Pick or type an output folder (default: `~/nuke_publishes`).
   - Set a shot name (auto-filled from script name when possible).
   - Tick or untick "Generate MP4".
4. Press **OK**. The tool will render frames, create the MP4 if requested, and write the JSON metadata.
5. Check the output folder for the image sequence, the MP4 and a `shot_v###_publish_meta.json` file.

---

## File and JSON format

- EXR pattern: `shotName_v001.0001.exr, ...` (4-digit frame padding)
- MP4: `shotName_v001.mp4`
- Metadata: `shotName_v001_publish_meta.json` with keys: `shot`, `version`, `author`, `rendered_at`, `frames`, `sequence_pattern`, `mp4`

Example JSON (short):

```json
{
  "shot": "shot01",
  "version": "v001",
  "author": "aditya",
  "rendered_at": "2025-08-08T23:00:00Z",
  "frames": {"first": 1001, "last": 1040},
  "sequence_pattern": "/path/to/shot01_v001.%04d.exr",
  "mp4": "/path/to/shot01_v001.mp4"
}
```

---

## Troubleshooting

- **Tool not visible in menu**: Ensure `menu.py` and `nuke_publish.py` are inside a folder that Nuke loads (usually `~/.nuke`). Restart Nuke after changes.
- ``** on import**: Check the exact file name and spelling; run `import nuke_publish` in Script Editor to see the trace.
- **ffmpeg not found**: Install `ffmpeg` and make sure `ffmpeg` is on your PATH. Test with `ffmpeg -version` in a shell.
- **Permission errors writing files**: Verify the output path exists and you have write permission.
- **Script throws an exception at startup**: Run Nuke from a terminal to see the startup traceback, or open the Script Editor to inspect the error.

---

## Packaging for Nukepedia / public release

Suggested repo/folder structure you can zip and upload:

```
nuke_publish_v001/
  ├─ nuke_publish.py
  ├─ menu.py
  ├─ README.md  <-- this file
  ├─ LICENSE   <-- e.g. MIT
  ├─ screenshots/  <-- GIF + PNGs sized for quick viewing
  └─ samples/  <-- small .nk example, optional
```

**Tips for listing on Nukepedia**

- Include a short description and what's unique about your tool.
- Add clear install instructions (where to copy files and how to add menu entries).
- Include compatibility (Nuke versions) and requirements (ffmpeg, Python version).
- Add at least one GIF (8–15s) showing the publish action and the output folder.
- Pick the right category (Python / Import & Export / UI) and add relevant tags (`Nuke`, `Python`, `pipeline`, `publish`).

---

## License (suggestion)

Use a permissive license like MIT so studios can quickly trial the tool. Example `LICENSE` snippet is included in the repo.

---

## Development roadmap (ideas for next posts)

1. Support re-using an existing `Write` node instead of creating a new one.
2. Add progress dialog and error handling reporting in the panel.
3. Generate a small HTML index/dashboard that lists all publishes from a folder.
4. Create the Houdini counterpart HDA that writes the same metadata schema.

---

## Contact

If you use this tool in production or want improvements, open an issue in the repo or DM me on LinkedIn / GitHub.

---

*End of README*

