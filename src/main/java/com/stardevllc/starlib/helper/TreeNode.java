package com.stardevllc.starlib.helper;

import java.util.LinkedList;

/**
 * Represents a node in a tree structure, or the root node
 *
 * @param <T> The data type
 */
public class TreeNode<T> {
    private T data;
    private TreeNode<T> parent;
    private final LinkedList<TreeNode<T>> children = new LinkedList<>();
    
    /**
     * Constructs a new node
     *
     * @param data     The data
     * @param parent   The parent
     * @param children The children
     */
    public TreeNode(T data, TreeNode<T> parent, LinkedList<TreeNode<T>> children) {
        this.data = data;
        this.parent = parent;
        this.children.addAll(children);
    }
    
    /**
     * Constructs a new node
     *
     * @param data   The data
     * @param parent The parent
     */
    public TreeNode(T data, TreeNode<T> parent) {
        this.data = data;
        this.parent = parent;
    }
    
    /**
     * Gets the data
     *
     * @return The data
     */
    public T getData() {
        return data;
    }
    
    /**
     * Sets the data
     *
     * @param data The new data
     */
    public void setData(T data) {
        this.data = data;
    }
    
    /**
     * Gets the parent
     *
     * @return The parent or null
     */
    public TreeNode<T> getParent() {
        return parent;
    }
    
    /**
     * Sets the parent
     *
     * @param parent The new parent
     */
    public void setParent(TreeNode<T> parent) {
        this.parent = parent;
    }
    
    /**
     * Gets a copy of the children
     *
     * @return The children
     */
    public LinkedList<TreeNode<T>> getChildren() {
        return new LinkedList<>(children);
    }
    
    /**
     * Adds a child to this node
     *
     * @param child The child node
     */
    public void addChild(TreeNode<T> child) {
        this.children.add(child);
        child.setParent(this);
    }
    
    /**
     * Adds a child to this node
     *
     * @param child The child data
     * @return The created tree node
     */
    public TreeNode<T> addChild(T child) {
        TreeNode<T> childNode = new TreeNode<>(child, this);
        this.children.add(childNode);
        return childNode;
    }
    
    /**
     * Addds a child to this tree structure
     *
     * @param parent the parent of the child
     * @param child  The child for the parent
     * @return The created tree node of the child
     */
    public TreeNode<T> addChild(T parent, T child) {
        if (parent.equals(this.data)) {
            return addChild(child);
        } else {
            for (TreeNode<T> childNode : this.children) {
                TreeNode<T> addedNode = childNode.addChild(parent, child);
                if (addedNode != null) {
                    return addedNode;
                }
            }
        }
        
        return null;
    }
    
    /**
     * Removes a child from this node
     * @param child the child
     * @return The removed node or null if it didn't exist
     */
    public TreeNode<T> removeChild(TreeNode<T> child) {
        if (this.children.remove(child)) {
            return child;
        }
        
        return null;
    }
    
    /**
     * Retrieves the tree node of the data
     * @param data The data to search for
     * @return The node or null
     */
    public TreeNode<T> search(T data) {
        if (this.data.equals(data)) {
            return this;
        }
        
        for (TreeNode<T> child : this.children) {
            TreeNode<T> result = child.search(data);
            if (result != null) {
                return result;
            }
        }
        
        return null;
    }
    
    /**
     * Removes the data fromt his tree
     * @param data The data to remove
     * @return The node that was removed
     */
    public TreeNode<T> remove(T data) {
        TreeNode<T> result = search(data);
        if (result != null) {
            if (result.getParent() == null) {
                return null; //Trying to remove the root node, this shouldn't be the case
            }
            
            return result.getParent().removeChild(result);
        }
        return null;
    }
    
    /**
     * Mainly testing, no customizibility
     * @return The heirarchy
     */
    public String heirarchy() {
        StringBuilder sb = new StringBuilder(String.valueOf(this.data));
        if (getParent() == null) {
            sb.append("->Root");
        } else {
            sb.append("->").append(getParent().heirarchy());
        }
        return sb.toString();
    }
    
    @Override
    public String toString() {
        return "TreeNode{" + "data=" + data +
                ", parent=" + (parent != null ? parent.data : "null") +
                ", children=" + children +
                '}';
    }
}
