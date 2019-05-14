from collections import defaultdict

from decimal import Decimal

import itertools

from rosalind.constants import AA_mass_table, rna_to_protein_dict, DNA_stop_codons, DNA_codon_table


def compute_complete_spectrum(protein_string):
	spectrum = defaultdict(int)
	mass_sum = Decimal(0)
	for c in protein_string[:-1]:
		mass_sum += AA_mass_table[c]
		spectrum[mass_sum] += 1
	mass_sum = Decimal(0)
	for c in reversed(protein_string):
		mass_sum += AA_mass_table[c]
		spectrum[mass_sum] += 1
	return spectrum


def get_best_aa_candidate(mass):
	""":return the AA that is closest to to given mass, as well as the mass-difference"""
	best_aa, best_diff = None, float("inf")
	for aa, m in AA_mass_table.items():
		diff = abs(m - mass)
		if diff < best_diff:
			best_aa, best_diff = aa, diff
	return best_aa, best_diff


def compute_ps_mass(ps):
	"""computes total mass of protein string"""
	return sum(AA_mass_table[it] for it in ps)



def dna_to_protein_string(dna):
	res = ""
	for i in range(0, len(dna) - 2, 3):
		key = dna[i:i + 3]
		if key in DNA_stop_codons:
			return res
		res += DNA_codon_table[key]
	return None


def rna_to_protein_string(rna):
	res = ""
	while len(rna) > 3:
		if rna[:3] not in rna_to_protein_dict:
			break
		res += rna_to_protein_dict[rna[:3]]
		rna = rna[3:]
	return res
