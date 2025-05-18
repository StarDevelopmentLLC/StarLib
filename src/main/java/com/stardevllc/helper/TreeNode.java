package com.stardevllc.helper;

import java.util.LinkedList;

public class TreeNode<T> {
    protected T data;
    protected TreeNode<T> parent;
    protected final LinkedList<TreeNode<T>> children = new LinkedList<>();

    public TreeNode(T data, TreeNode<T> parent, LinkedList<TreeNode<T>> children) {
        this.data = data;
        this.parent = parent;
        this.children.addAll(children);
    }
    
    public TreeNode(T data, TreeNode<T> parent) {
        this.data = data;
        this.parent = parent;
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
        this.children.add(child);
        child.setParent(this);
    }
    
    public TreeNode<T> addChild(T child) {
        TreeNode<T> childNode = new TreeNode<>(child, this);
        this.children.add(childNode);
        return childNode;
    }
    
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
        return result;
    }
    
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
