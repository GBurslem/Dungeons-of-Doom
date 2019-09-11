/** This is the interface used by the graphical user interfaces for clients and servers. 
 * 
 * 
 * @author George P Burslem
 * @version 1.0
 * @release 22/03/2016
 */

public interface DungeonsUI {
	void setupNetwork();
	String ipSelection();
	int portSelection();
	void setupMap();
	String getMap();
}
