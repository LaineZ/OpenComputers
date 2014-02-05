package li.cil.oc.api.network;

/**
 * The environment of a node.
 * <p/>
 * For blocks/tile entities this will usually be the tile entity. For items
 * this will usually be an object created when a component is added to a
 * compatible inventory (e.g. put into a computer).
 * <p/>
 * Tile entities should implement this interface if they want to be connected
 * to the component network of their neighboring blocks. If you cannot do that,
 * as mentioned above, you will have to provide a driver that creates a managed
 * environment for the block you wish to connect instead.
 * <p/>
 * To get some more control over which sides of your block may connect to a
 * network, see {@link SidedEnvironment}.
 * <p/>
 * When a tile entity implements this interface a good way of connecting and
 * disconnecting is the following pattern:
 * <pre>
 *     void updateEntity() {
 *         super.updateEntity()
 *         if (node != null && node.network == null) {
 *             api.Network.joinOrCreateNetwork(this);
 *         }
 *     }
 *
 *     void onChunkUnload() {
 *         super.onChunkUnload()
 *         if (node != null) node.remove()
 *     }
 *
 *     void invalidate() {
 *         super.invalidate()
 *         if (node != null) node.remove()
 *     }
 * </pre>
 * <p/>
 * Item environments are always managed, so you will always have to provide a
 * driver for items that should interact with the component network.
 * <p/>
 * To interact with environments from user code you will have to do two things:
 * <ol>
 * <li>Make the environment's {@link #node} a {@link Component} and ensure
 * its {@link Component#visibility} is set to a value where it can
 * be seen by computers in the network.</li>
 * <li>Annotate methods in the environment as {@link Callback}s.</li>
 * </ol>
 */
public interface Environment {
    /**
     * The node this environment wraps.
     * <p/>
     * The node is the environments gateway to the component network, and thus
     * its preferred way to interact with other components in the same network.
     *
     * @return the node this environment wraps.
     */
    Node node();

    /**
     * This is called when a node is added to a network.
     * <p/>
     * This is also called for the node itself, if it was added to the network.
     * <p/>
     * At this point the node's network is never <tt>null</tt> and you can use
     * it to query it for other nodes. Use this to perform initialization logic,
     * such as building lists of nodes of a certain type in the network.
     */
    void onConnect(Node node);

    /**
     * This is called when a node is removed from the network.
     * <p/>
     * This is also called for the node itself, when it has been removed from
     * its network.
     * <p/>
     * At this point the node's network is no longer available (<tt>null</tt>).
     * Use this to perform clean-up logic such as removing references to the
     * removed node.
     */
    void onDisconnect(Node node);

    /**
     * This is the generic message handler.
     * <p/>
     * It is called whenever this environments {@link Node} receives a message
     * that was sent via one of the <tt>send</tt> methods in the {@link Network}
     * or the <tt>Node</tt> itself.
     *
     * @param message the message to handle.
     */
    abstract void onMessage(Message message);
}