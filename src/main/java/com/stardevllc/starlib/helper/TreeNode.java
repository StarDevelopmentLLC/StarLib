package com.stardevllc.starlib.helper;

import java.util.*;

/**
 * Class for representing a tree structure
 * @param <T> The data type
 * @deprecated See {@link com.stardevllc.starlib.tree.TreeNode} for new things
 */
@Deprecated
public class TreeNode<T> implements Iterable<TreeNode<T>> {
    private T data;
    private TreeNode<T> parent;
    private final LinkedHashSet<TreeNode<T>> children = new LinkedHashSet<>();
    private int depth;
    
    public TreeNode(T data, TreeNode<T> parent, int depth, Collection<TreeNode<T>> children) {
        this.data = data;
        this.parent = parent;
        this.depth = depth;
        if (children != null) {
            this.children.addAll(children);
        }
    }
    
    public TreeNode(T data, TreeNode<T> parent, LinkedHashSet<TreeNode<T>> children) {
        this(data, parent, 0, children);
    }
    
    public TreeNode(T data, TreeNode<T> parent, int depth) {
        this(data, parent, depth, null);
    }
    
    public TreeNode(T data, TreeNode<T> parent) {
        this(data, parent, null);
    }
    
    public TreeNode(T data, int depth) {
        this(data, null, depth, null);
    }
    
    public TreeNode(T data) {
        this(data, null);
    }
    
    public TreeNode() {
        
    }
    
    public int getDepth() {
        return depth;
    }
    
    public T getData() {
        return data;
    }
    
    public void setData(T data) {
        this.data = data;
    }
    
    public TreeNode<T> getParent() {
        return parent;
    }
    
    public void setParent(TreeNode<T> parent) {
        this.parent = parent;
    }
    
    public LinkedList<TreeNode<T>> getChildren() {
        return new LinkedList<>(children);
    }
    
    public void addChild(TreeNode<T> child) {
        child.depth = this.depth + 1;
        this.children.add(child);
        child.setParent(this);
    }
    
    public TreeNode<T> addChild(T child) {
        TreeNode<T> childNode = new TreeNode<>(child, this);
        childNode.depth = this.depth + 1;
        this.children.add(childNode);
        return childNode;
    }
    
    public TreeNode<T> addChild(T parent, T child) {
        if (this.data == null && parent == null) {
            return addChild(child);
        } else if (parent.equals(this.data)) {
            return addChild(child);
        } else {
            for (TreeNode<T> childNode : this.children) {
                TreeNode<T> addedNode = childNode.addChild(parent, child);
                if (addedNode != null) {
                    addedNode.depth = childNode.depth + 1;
                    return addedNode;
                }
            }
        }
        
        return null;
    }
    
    @SafeVarargs
    public final List<TreeNode<T>> addChildren(T parent, T firstChild, T... otherChildren) {
        List<TreeNode<T>> nodes = new ArrayList<>();
        
        nodes.add(addChild(parent, firstChild));
        if (otherChildren != null) {
            for (T otherChild : otherChildren) {
                nodes.add(addChild(parent, otherChild));
            }
        }
        
        return nodes;
    }
    
    public TreeNode<T> removeChild(TreeNode<T> child) {
        if (this.children.remove(child)) {
            return child;
        }
        
        return null;
    }
    
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
    
    public boolean hasChildren() {
        return !this.children.isEmpty();
    }
    
    public List<TreeNode<T>> getLeafNodes() {
        List<TreeNode<T>> leafNodes = new LinkedList<>();
        for (TreeNode<T> node : this) {
            if (!node.hasChildren()) {
                leafNodes.add(node);
            }
        }
        
        return leafNodes;
    }
    
    @Override
    public String toString() {
        return "TreeNode{" + "data=" + data +
                ", parent=" + (parent != null ? parent.data : "null") +
                ", children=" + children +
                '}';
    }
    
    public List<TreeNode<T>> getAllChildren() {
        List<TreeNode<T>> children = new ArrayList<>(this.children);
        for (TreeNode<T> child : this.children) {
            children.addAll(child.getAllChildren());
        }
        
        return children;
    }
    
    @Override
    public Iterator<TreeNode<T>> iterator() {
        List<TreeNode<T>> nodes = new ArrayList<>();
        nodes.add(this);
        nodes.addAll(this.getAllChildren());
        
        return nodes.iterator();
    }
    
    @Override
    public boolean equals(Object object) {
        if (object == null || getClass() != object.getClass()) {
            return false;
        }
        
        TreeNode<?> treeNode = (TreeNode<?>) object;
        return Objects.equals(data, treeNode.data) && Objects.equals(parent, treeNode.parent);
    }
    
    @Override
    public int hashCode() {
        int result = Objects.hashCode(data);
        result = 31 * result + Objects.hashCode(parent);
        return result;
    }
}