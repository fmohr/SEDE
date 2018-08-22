import setuptools

dependencies = [
      'ujson',
      'requests'
]

setuptools.setup(name='SEDE-executor',
      version='0.0.3',
      description='SEDE python executor',
      url='http://github.com/fmohr/SEDE',
      author='Amin Faez',
      author_email='aminfaez@mail.upb.de',
      license='GNU',
      long_description_content_type="text/markdown",
      packages=setuptools.find_packages(),
      install_requires=dependencies,
      entry_points=
            {
                  'console_scripts': [
                        'pyexecutor = exe.httpexecutor:main'
                  ]
            })