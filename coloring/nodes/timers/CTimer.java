package projects.coloring.nodes.timers;
import projects.coloring.nodes.nodeImplementations.CNode;
import projects.coloring.nodes.messages.*;
import sinalgo.nodes.timers.Timer;
public class CTimer extends Timer {
	CNode sender;
	int interval;
	public CTimer(CNode sender, int interval) {
		this.sender = sender;
		this.interval = interval;
	}
	// called upon timer expiration
	public void fire() {
		// create message with color
		CMessage msg= new CMessage(sender.ID,sender.getCouleur());
		sender.broadcast(msg); // send to all neighbors
		this.startRelative(interval, node);
		// recursive restart of the timer
	}
}
