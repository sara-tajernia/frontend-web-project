from collections import defaultdict

class Graph:
    def __init__(self, vertices):
        self.V = vertices
        self.graph = defaultdict(list)

    def addEdge(self, u, v):
        self.graph[u].append(v)

def input(address):
    text = open(address, "r")
    rows, cols = map(int, text.readline().split())
    table = [text.readline().split() for j in range(rows)]
    print(table)
    return table, cols


def map_IDS(table, cols):
    check = False
    for i in range(len(table)):
        for j in range (cols):
                if 'r' in table[i][j]:
                    xR , yR = i, j
                    check = True
                    break
        if check : break
    g = Graph(len(table)*cols)
    explored, frontier = [], []
    frontier.append(xR * cols +yR)
    while len(frontier) >0:
        # print('1', node)
        xDis, yDis = int(frontier[0]/cols), frontier[0]%cols
        # print(xDis, yDis)
        if 0 < xDis < len(table) and ((xDis - 1) * cols + yDis) not in explored:       #up
            # print(1,'          ', xDis-1, yDis)
            g.addEdge(xDis * cols + yDis, (xDis - 1) * cols + yDis)
            if ((xDis - 1) * cols + yDis) not in frontier:
                frontier.append((xDis - 1) * cols + yDis)
        if 0 <= xDis < len(table)-1 and ((xDis + 1) * cols + yDis) not in explored:  # down
            # print(2,'          ', xDis+1, yDis)
            g.addEdge(xDis * cols + yDis, (xDis + 1) * cols + yDis)
            if ((xDis + 1) * cols + yDis) not in frontier:
                frontier.append((xDis + 1) * cols + yDis)
        if 0 < yDis < cols and (xDis * cols + yDis - 1) not in explored:       #left
            # print(3,'          ', xDis, yDis-1)
            g.addEdge(xDis * cols + yDis, xDis * cols + yDis-1)
            if (xDis * cols + yDis - 1) not in frontier:
                frontier.append(xDis * cols + yDis - 1)
        if 0 <= yDis < cols-1 and (xDis * cols + yDis + 1) not in explored:        #right
            # print(4,'          ', xDis, yDis+1)
            g.addEdge(xDis * cols + yDis, xDis * cols + yDis+1)
            if (xDis * cols + yDis + 1) not in frontier:
                frontier.append(xDis * cols + yDis + 1)
        if (xDis * cols + yDis) not in explored:
            explored.append(xDis * cols + yDis)
        frontier.remove(xDis * cols + yDis)
        print('explored', explored)
        print('frontier', frontier)

    print(g.graph)

if __name__ == '__main__':
    table, cols = input("test1.txt")
    map_IDS(table, cols)
