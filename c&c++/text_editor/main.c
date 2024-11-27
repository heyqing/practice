#ifdef _WIN32
#include <io.h>
#else
#include <unistd.h>
#endif


int main() {
	char c;
	while (read(STDIN_FILENO, &c, 1) == 1);
	return 0;
}