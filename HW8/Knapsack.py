class Knapsack:
    def __init__(self,n,W,itemList):
        self.n = n
        self.W = W
        self.wList = list(map(lambda item: item[0],itemList))
        self.vList = list(map(lambda item: item[1],itemList))

    def _Knapsack(self,n,W, wList, vList):
        vOld = [0]*(W+1)
        vNew = [0]*(W+1)
        for i in range(n):
            for w in range(1,W+1):
                if w>=wList[i]:
                    vNew[w] = max(vOld[w], vOld[w-wList[i]] + vList[i])
                else:
                    vNew[w] = vOld[w]
            vOld = [j for j in vNew]
        return vNew[W]

    def compute(self):
        return self._Knapsack(self.n, self.W, self.wList, self.vList)


def main():
    numInstance = int(input())  # the number of instance, first input
    res = []
    for i in range(numInstance):
        n,W = list(map(int, input().split())) # the number of items and total weight limit in i-th instance

        itemId = 0
        items = []
        while itemId < n:
            items.append(list(map(int, input().split())))
            itemId += 1

        KS = Knapsack(n,W,items)
        res.append(KS.compute())
    for val in res:
        print(val)

if __name__ == "__main__":
    main()

