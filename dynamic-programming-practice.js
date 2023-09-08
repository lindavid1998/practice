// Find largest contiguous sum in an array
function findLargestContiguousSum(array) {
	if (array.length === 0) return 0;

	let sum = 0;
	let solutions = [];

	if (array[0] > 0) {
		solutions[0] = array[0];
	} else {
		solutions[0] = 0;
	}

	let i = 1;

	while (i < array.length) {
		sum = sum + array[i];
		if (sum > 0) {
			solutions[i] = solutions[i - 1] + sum;
			sum = 0;
		} else {
			solutions[i] = solutions[i - 1];
		}
		i++;
	}

	return solutions[array.length - 1];
}

// Find length of longest increasing subsequence of array
function getLIS(array) {

	const solutions = new Array(array.length);
	solutions[0] = 1; // base case

	for (let i = 0; i < array.length; i++) {
		maxLIS = -1;
		for (let j = 0; j < i; j++) {
			if (array[i] > array[j] && solutions[j] > maxLIS) {
				maxLIS = solutions[j];
			}
		}

		if (maxLIS == -1) {
			solutions[i] = 1;
		} else {
			solutions[i] = maxLIS + 1;
		}
	}

	return Math.max(...solutions);
}

// Given a rod of a certain length, find the maximum profit from cutting the rod
// into pieces and selling it 
const getMaxProfit = (n, prices, lengths) => {
	let solutions = new Array(n + 1);
	solutions.fill(-1);
	return rGetMaxProfit(n, prices, lengths, solutions);
};

const max = (a, b) => {
	return a > b ? a : b;
};

const rGetMaxProfit = (n, prices, lengths, S) => {
	if (n == 0) return 0;

	if (S[n] != -1) return S[n];

	let maxProfit = 0;

	for (let i = 0; i < lengths.length; i++) {
		remLength = n - lengths[i];
		if (remLength < 0) continue;

		profit = rGetMaxProfit(remLength, prices, lengths, S);
		totalProfit = prices[i] + profit;
		maxProfit = max(maxProfit, totalProfit);
	}

	S[n] = maxProfit;

	return S[n];
};


// Find fewest number of coins to give exact change
function coinChange(coins, amount) {
	if (amount === 0) return 0;

	let S = new Array(amount + 1);
	S.fill(-1);

	coins.forEach((coin) => {
		if (coin <= amount) S[coin] = 1;
	});

	return rCoinChange(S, amount, coins);
}

function compareNumbers(a, b) {
	return a - b;
}

function rCoinChange(S, V, coinValues) {
	coinValues = coinValues.sort(compareNumbers);

	for (let i = coinValues[0] + 1; i < S.length; i++) {
		if (S[i] == 1) continue;

		let min;

		for (let j = 0; j < coinValues.length; j++) {
			rem = i - coinValues[j];

			if (rem < 0) break;

			if (S[rem] != -1 && (!min || S[rem] < min)) {
				min = S[rem];
			}
		}

		S[i] = min ? min + 1 : -1;
	}

	return S[V];
}