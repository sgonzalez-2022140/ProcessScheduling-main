import os
import time

def clear():
    # Windows = cls, Linux/Mac = clear
    os.system("cls" if os.name == "nt" else "clear")

frames = [
r"""
         PERREO TIME 🔥

            \o/
             |
            / \
""",
r"""
         PERREO TIME 🔥

            \o_
             |\
            / \
""",
r"""
         PERREO TIME 🔥

            _o/
           /|
            / \
""",
r"""
         PERREO TIME 🔥

            \o_
             |\
            _/ \
""",
r"""
         PERREO TIME 🔥

            _o/
           /|
            /\_
""",
r"""
         PERREO TIME 🔥

            \o/
             |
           _/ \
"""
]

try:
    while True:
        for frame in frames:
            clear()
            print(frame)
            time.sleep(0.18)  # ajusta velocidad si quieres
except KeyboardInterrupt:
    clear()
    print("Perreo pausado 😎")
