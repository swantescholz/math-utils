from typing import Callable

import matplotlib

matplotlib.use("TkAgg")
import matplotlib.pyplot as plt
import random


class _Cell:
	def __init__(self, txt):
		self.txt: str = txt
		self.element = None


class NumberGridPlotter:
	def __init__(self, w: int, h: int = None, fontsize=12):
		if h is None:
			h = w
		self.w = w
		self.h = h
		self.fontsize = fontsize
		self.texts = [[_Cell(f"{x},{y}") for x in range(self.w)] for y in range(self.h)]
		self.fig = None
		self.ax = None
	
	def set_text(self, x, y, new_text):
		self.texts[y][x].txt = new_text
	
	def _draw(self):
		for y in range(self.h):
			for x in range(self.w):
				cell = self.texts[y][x]
				if cell.element is not None:
					cell.element.remove()
				cell.element = plt.text(x, y, cell.txt, fontsize=self.fontsize)
		self.fig.canvas.draw()
	
	def show(self, on_next: Callable[[], None] = lambda: None):
		def on_key_press(event):
			if event.key == 'escape':
				plt.close(event.canvas.figure)
			else:
				on_next()
				self._draw()
		
		self.fig, self.ax = plt.subplots()
		self.ax.axis([-0.2, self.w, -0.2, self.h])
		self.fig.canvas.mpl_connect('key_press_event', on_key_press)
		self._draw()
		self.ax.set_xticks(range(self.w + 1))
		self.ax.set_yticks(range(self.h + 1))
		plt.grid()
		plt.show()


if __name__ == "__main__":
	g = NumberGridPlotter(10, 5, fontsize=10)
	
	
	def update():
		for y in range(g.h):
			for x in range(g.w):
				n = random.randrange(100)
				g.set_text(x, y, f"AB {n}")
	
	
	g.show(update)
