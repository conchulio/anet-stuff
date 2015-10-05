package projects.coloring.nodes.messages;

import sinalgo.nodes.messages.Message;

public class CMessage extends Message {
	public int id;
	public int color;
	public CMessage(int id, int color) {
		this.id=id;
		this.color = color;}
		public Message clone() {
		return new CMessage(id,color);
	}
}
