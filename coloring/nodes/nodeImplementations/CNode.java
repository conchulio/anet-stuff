package projects.coloring.nodes.nodeImplementations;
import java.awt.Color;
import java.awt.Graphics;
import java.util.*;
import sinalgo.configuration.WrongConfigurationException;
import sinalgo.gui.transformation.PositionTransformation;
import sinalgo.nodes.Node;
import sinalgo.nodes.edges.Edge;
import sinalgo.nodes.messages.Inbox;
import projects.coloring.nodes.timers.*;
import projects.coloring.nodes.messages.*;
import sinalgo.nodes.messages.Message;

class state {
	int color;
	state(int color){this.color=color;}
}
public class CNode extends Node {
	private int color;
	private final int nb = 10;
	private final Color tab[] ={Color.BLUE,Color.CYAN,Color.GREEN,Color.LIGHT_GRAY,Color.MAGENTA,Color.ORANGE,Color.PINK,Color.RED,Color.WHITE,Color.YELLOW};
	private Hashtable<Integer,state> neighborStates;
	public int getCouleur(){
		return color;
	}
	public Color RGBCouleur(){
		return tab[getCouleur()];
	}
	public void setCouleur(int c) {
		this.color=c;
	}
	public void initColor(int range){
		setCouleur((int) (Math.random() * range) % range);
	}
	public void compute(){
		boolean same=false;
		Iterator<Edge> it=this.outgoingConnections.iterator();
		boolean SC[]=new boolean[nb];
		for (int i=0;i<SC.length;i++)
			SC[i]=false;
		while(it.hasNext()){
			Edge e=it.next();
			state tmp=neighborStates.get(new Integer(e.endNode.ID));
			if(tmp!=null){
				if(tmp.color==this.getCouleur()){
					same=true;
				}
				SC[tmp.color]=true;
			}
		}
		if (same){
			int dispo=0;
			for (int i=0;i<SC.length;i++)
				if(SC[i]==false) dispo++;
			if (dispo == 0) return;
			int choix= ((int) (Math.random() * 10000)) % dispo + 1;
			int i=0;
			while(choix > 0){
				if(SC[i]==false)
				choix--;
				if(choix>0) i++;
			}
			this.setCouleur(i);
		}
	}
	public void handleMessages(Inbox inbox) {
		if(inbox.hasNext()==false) return;
		while(inbox.hasNext()){
			Message msg=inbox.next();
			if(msg instanceof CMessage){
				state tmp=new state(((CMessage) msg).color);
				neighborStates.put(new Integer(((CMessage) msg).id),tmp);
				compute();
			}
		}
	}

	public void preStep() {}

	public void init() {
		initColor(nb);
		(new CTimer(this,50)).startRelative(50,this);
		this.neighborStates = new Hashtable<Integer,state> (this.outgoingConnections.size());
	}
	public void neighborhoodChange() {}
	public void postStep() {}
	public String toString() {
		String s = "Node(" + this.ID + ") [";
		Iterator<Edge> edgeIter = this.outgoingConnections.iterator();
		while(edgeIter.hasNext()){
			Edge e = edgeIter.next();
			Node n = e.endNode;
			s+=n.ID+" ";
		}
		return s + "]";
	}
	public void checkRequirements() throws WrongConfigurationException {}
	public void draw(Graphics g,PositionTransformation pt,boolean highlight) {
		Color c;
		this.setColor(this.RGBCouleur());
		String text = ""+this.ID;
		c=Color.BLACK;
		super.drawNodeAsDiskWithText(g, pt, highlight, text, 20, c);
	}
}
